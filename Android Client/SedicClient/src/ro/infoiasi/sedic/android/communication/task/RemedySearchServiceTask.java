package ro.infoiasi.sedic.android.communication.task;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.communication.event.RemedySearchEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import ro.infoiasi.sedic.android.model.DrugBean;
import ro.infoiasi.sedic.android.model.RemedyBean;
import ro.infoiasi.sedic.android.util.JSONHelper;
import ro.infoiasi.sedic.android.util.URLConstants;
import android.util.Log;

public class RemedySearchServiceTask extends ServiceTask<RemedyBean> {

    @SuppressWarnings("unused")
    private static final String tag = RemedySearchServiceTask.class.getSimpleName();
    private List<DrugBean> adjuvants;
    private List<DiseaseBean> therapeutics;

    public RemedySearchServiceTask(List<DrugBean> adjuvants, List<DiseaseBean> therapeutics) {
        this.adjuvants = adjuvants;
        this.therapeutics = therapeutics;
    }

    @Override
    public HttpRequestMethod getHttpRequestMethod() {
        return HttpRequestMethod.POST;
    }

    @Override
    public String getURL() {
        return URLConstants.URL_RESOURCE_REMEDY;
    }

    @Override
    public void prepareRequest(HttpRequestBase request) {
        HttpPost postRequest = (HttpPost) request;
        StringEntity requestEntity;
        try {
            requestEntity = new StringEntity(buildPayloadJSON());
            postRequest.setEntity(requestEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response<RemedyBean> parseResponse(String response) {
        Map<Long, RemedyBean> outputList = null;
        try {
            JSONObject jsonResponse = new JSONObject(response);
            outputList = JSONHelper.parseCompactRemedyArray(jsonResponse.getJSONArray("remedies").toString());
            Log.e("debug", outputList.values().toString());
        } catch (JSONException e) {

        }

        if (outputList != null) {
            return new Response<RemedyBean>(this, Response.ResponseStatus.OK, outputList);
        } else {
            Response<RemedyBean> output = new Response<RemedyBean>(this, Response.ResponseStatus.FAILED);
            output.setErrorMessage("Could not parse json response");
            return output;
        }
    }

    @Override
    public ResponseEvent getEvent(Response<RemedyBean> response) {
        return new RemedySearchEvent(response);
    }

    private String buildPayloadJSON() {
        JSONObject output = new JSONObject();

        JSONArray therapeuticsArray = new JSONArray();
        JSONArray adjuvantsArray = new JSONArray();

        if (adjuvants != null) {
            for (DrugBean drug : adjuvants) {
                JSONObject drugJSON = new JSONObject();
                try {
                    drugJSON.put("id", drug.getDrugId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    drugJSON.put("uri", drug.getDrugURI());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if (therapeutics != null) {
            for (DiseaseBean disease : therapeutics) {
                JSONObject diseaseJSON = new JSONObject();
                try {
                    diseaseJSON.put("id", disease.getDiseaseId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    diseaseJSON.put("uri", disease.getDiseaseURI());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            output.put("adjuvant_effect", adjuvantsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            output.put("therapeutical_effect", therapeuticsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

}
