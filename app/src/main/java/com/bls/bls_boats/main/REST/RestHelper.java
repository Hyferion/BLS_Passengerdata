package com.bls.bls_boats.main.REST;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.bls.bls_boats.main.Entity.SpecialFrequencyRecord;
import com.bls.bls_boats.main.Entity.FrequencyRecord;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;


/**
 * Helper class for REST Connection, writing and reading from REST-API
 */
public class RestHelper {

    private String bearer;
    private URL postEndPoint;
    private URL getEndPoint;
    private HttpsURLConnection connection;
    private Context context;


    public RestHelper(URL getEndPoint, URL postEndPoint, String bearer, Context context) {
        this.context = context;
        this.postEndPoint = postEndPoint;
        this.getEndPoint = getEndPoint;
        this.bearer = bearer;
    }

    public void openConnection() {
        loadCertificateForConnection();
    }

    public void postRequest(String json) throws IOException {
        connection = (HttpsURLConnection) postEndPoint.openConnection();
        setPOSTHeaders();
        transmitErfassung(json);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getRequest() throws IOException {
        connection = (HttpsURLConnection) getEndPoint.openConnection();
        setGETHeaders();
        return getInputStream();
    }

    private static KeyStore loadKeystore(byte[] keystoreBytes)
            throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore ks;
        ks = KeyStore.getInstance("JKS");
        if (keystoreBytes != null) {
            ks.load(new ByteArrayInputStream(keystoreBytes), "android".toCharArray());
        } else {
            ks.load(null);
        }
        return ks;


    }

    private void loadCertificateForConnection() {
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            InputStream certInput = new BufferedInputStream(context.getResources().openRawResource(context.getResources().getIdentifier("raw/shop_bls_cert", "raw", context.getPackageName())));
            clientStore.load(certInput, "bls_shop_request".toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(clientStore, "shop_bls_request".toCharArray());
            KeyManager[] kms = kmf.getKeyManagers();

            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kms, null, null);
            SSLContext.setDefault(sslContext);

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            connection.setRequestProperty("Authentication", bearer);
            connection.setSSLSocketFactory(sslContext.getSocketFactory());


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void transmitErfassung(String json) throws IOException {
        createOutputStream(json);

        if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed: HTTP error code : " + connection.getResponseCode());
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String output;
        while ((output = bufferedReader.readLine()) != null) {
            Log.d("OUTPUT", "OUTPUT REST: " + output);
        }

        endConnection();

    }


    private void setPOSTHeaders() throws ProtocolException {
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
    }

    private void setGETHeaders() throws ProtocolException {
        connection.setDoOutput(false);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getInputStream() throws IOException {
        if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed: HTTP error code : " + connection.getResponseCode());
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //We require at least Java 8 for this transformation
        String json = bufferedReader.lines().collect(Collectors.joining());

        String output;
        while ((output = bufferedReader.readLine()) != null) {
            Log.d("OUTPUT", "OUTPUT REST: " + output);

        }


        endConnection();
        return json;
    }

    private void createOutputStream(String json) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(json.getBytes());
    }

    public String frequenzErfassungenToJSON(List frequezerfassungen, String schiffName) throws JsonProcessingException {
        try {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", Integer.toString(((FrequencyRecord) frequezerfassungen.get(0)).getFrequencyRecordGroupNumber()));
            jsonObj.put("date", formatter.format(date));
            jsonObj.put("vesselName", schiffName);

            if (frequezerfassungen instanceof FrequencyRecord) {
                JSONArray jsonArray = new JSONArray();
                for (Object freq : frequezerfassungen) {
                    FrequencyRecord frequezerfassung = (FrequencyRecord) freq;
                    JSONObject freqObj = new JSONObject();
                    if (frequezerfassung.getNotOperated()) {
                        freqObj.put("name", frequezerfassung.getStationName());
                        freqObj.put("skipped", frequezerfassung.getReasonText());
                    } else {
                        freqObj.put("name", frequezerfassung.getStationName());
                        if (frequezerfassung.getBoardingCount() != 0) {
                            freqObj.put("departureTime", frequezerfassung.getDepartueTime());
                            freqObj.put("passengersIn", frequezerfassung.getBoardingCount());
                        }
                        if (frequezerfassung.getDeboardingCount() != 0) {
                            freqObj.put("arrivalTime", frequezerfassung.getArrivalTime());
                            freqObj.put("oupassengersOut", frequezerfassung.getDeboardingCount());
                        }
                        jsonArray.put(freqObj);
                    }
                }

                jsonObj.put("peers", jsonArray);
            } else {
                for (Object freq : frequezerfassungen) {
                    //Nicht klar ob benötigt
                    SpecialFrequencyRecord frequezerfassung = (SpecialFrequencyRecord) freq;
                }
            }
            jsonObj.put("weather", "sunny");

            return jsonObj.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    private void endConnection() {
        connection.disconnect();
    }

    public URL getPostEndPoint() {
        return postEndPoint;
    }

    public void setPostEndPoint(URL postEndPoint) {
        this.postEndPoint = postEndPoint;
    }

    public void setConnection(HttpsURLConnection connection) {
        this.connection = connection;
    }

    public HttpsURLConnection getConnection() {
        return connection;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }
}
