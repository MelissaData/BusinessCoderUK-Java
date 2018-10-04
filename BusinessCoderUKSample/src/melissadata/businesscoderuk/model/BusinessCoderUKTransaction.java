package melissadata.businesscoderuk.model;

import org.apache.sling.commons.json.JSONObject;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class BusinessCoderUKTransaction {
    private final String endpoint;
    private String identNumber;
    private String companyName, addressLine1, addressLine2, addressLine3, addressLine4, locality, administrativeArea, postalCode, country;
    private boolean selectAll, grpAddressDetails, grpFirmographics, grpGeoCode;
    private String format;

    public BusinessCoderUKTransaction() {
        endpoint = "https://globalbusinesscoder.melissadata.net/WEB/BusinessCoder/doBusinessCoderGB?";
        identNumber = "";
        companyName = "";
        addressLine1 = "";
        addressLine2 = "";
        addressLine3 = "";
        addressLine4 = "";
        locality = "";
        administrativeArea = "";
        postalCode = "";
        country = "";
        format = "";
    }

    public String processTransaction(String request) {
        String response = "";
        URI uri;
        URL url;
        try {
            uri = new URI(request);
            url = new URL(uri.toURL().toString());

            HttpURLConnection urlConn = (HttpURLConnection)(url.openConnection());

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringReader reader;
            StringWriter writer = new StringWriter();
            StringBuilder jsonResponse = new StringBuilder();
            String inputLine = "";

            if (format.equals("XML"))
            {
                String xmlLine = "";
                String xmlString = "";

                while((xmlLine = in.readLine()) != null) {
                    xmlString += xmlLine + "\n";
                }

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t = tf.newTransformer();
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");
                t.setOutputProperty(OutputKeys.INDENT, "yes");

                reader = new StringReader(xmlString);
                try {
                    t.transform(new javax.xml.transform.stream.StreamSource(reader), new javax.xml.transform.stream.StreamResult(writer));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                response = writer.toString();
            } else {
                while ((inputLine = in.readLine()) != null) {
                    jsonResponse.append(inputLine);
                }
                @SuppressWarnings("deprecation")
                JSONObject test = new JSONObject(jsonResponse.toString());
                response = test.toString(10);
            }
        } catch (Exception e){
            System.out.println("Error sending request: \n" + e);
        }
        return response;
    }

    public String generateRequestString() {
        String request = "";
        request = endpoint;
        request += "&id=" + getIdentNumber();
        request += "&cols=" + getColumns();

        try {
            if(!getCompanyName().equals(""))
                request += "&comp=" + URLEncoder.encode(getCompanyName(), "UTF-8");

            if(!getAddressLine1().equals(""))
                request += "&a1=" + URLEncoder.encode(getAddressLine1(), "UTF-8");

            if(!getAddressLine2().equals(""))
                request += "&a2=" + URLEncoder.encode(getAddressLine2(), "UTF-8");

            if(!getAddressLine3().equals(""))
                request += "&a3=" + URLEncoder.encode(getAddressLine3(), "UTF-8");

            if(!getAddressLine4().equals(""))
                request += "&a4=" + URLEncoder.encode(getAddressLine4(), "UTF-8");

            if(!getLocality().equals(""))
                request += "&loc=" + URLEncoder.encode(getLocality(), "UTF-8");

            if(!getAdministrativeArea().equals(""))
                request += "&area=" + URLEncoder.encode(getAdministrativeArea(), "UTF-8");

            if(!getPostalCode().equals(""))
                request += "&postal=" + URLEncoder.encode(getPostalCode(), "UTF-8");

            if(!getCountry().equals(""))
                request += "&ctry=" + URLEncoder.encode(getCountry(), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            //throw new UnsupportedEncodingException("Unsupported Encoding Exception: " + e);
        }

        request += "&format=" + getFormat();

        return request;
    }

    public String getColumns() {
        String columnString = "";
        if (isGrpAddressDetails()){
            columnString += "grpAddressDetail";
        }

        if (isGrpFirmographics() && columnString.equals(""))
            columnString += "grpFirmographics";
        else if (isGrpFirmographics() && !columnString.equals("")) {
            columnString += ",grpFirmographics";
        }

        if (isGrpGeoCode() && columnString.equals(""))
            columnString += "grpGeoCode";
        else if (isGrpGeoCode() && !columnString.equals("")) {
            columnString += ",grpGeoCode";
        }

        return columnString;
    }

    public String getIdentNumber() {
        return identNumber;
    }

    public void setIdentNumber(String identNumber) {
        this.identNumber = identNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(String administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isGrpAddressDetails() {
        return grpAddressDetails;
    }

    public void setGrpAddressDetails(boolean grpAddressDetails) {
        this.grpAddressDetails = grpAddressDetails;
    }

    public boolean isGrpFirmographics() {
        return grpFirmographics;
    }

    public void setGrpFirmographics(boolean grpFirmographics) {
        this.grpFirmographics = grpFirmographics;
    }

    public boolean isGrpGeoCode() {
        return grpGeoCode;
    }

    public void setGrpGeoCode(boolean grpGeoCode) {
        this.grpGeoCode = grpGeoCode;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }
}
