package org.kie.trustyai.payloads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.trustyai.explainability.model.FeatureImportance;
import org.kie.trustyai.explainability.model.Saliency;
import org.kie.trustyai.explainability.model.SaliencyResults;

public class CombinedSaliencyResponse extends SaliencyExplanationResponse{
    protected SaliencyResults limeResults;
    protected SaliencyResults shapResults;

    public CombinedSaliencyResponse(SaliencyResults limeResults, SaliencyResults shapResults){
        this.limeResults = limeResults;
        this.shapResults = shapResults;
    }

    public SaliencyResults limeResults(){
        return limeResults;
    }
    
    public SaliencyResults shapResults(){
        return shapResults;
    }

    public static CombinedSaliencyResponse fromSaliencyResults(SaliencyResults limeResults, SaliencyResults shapResults) {
        return new CombinedSaliencyResponse(limeResults, shapResults);
    }

    public String toString(){
        return "CombinedSaliencyExplanationResponse{" +
        "timestamp=" + timestamp +
        ", LIME saliencies=" + limeResults +
        ", SHAP saliencies=" + shapResults +
        '}';
    }

    private static Map<String, List<FeatureSaliency>> combinedSaliencyExplanationResponse(SaliencyResults limeResults, SaliencyResults shapResults){
        Map<String, List<FeatureSaliency>> combinedFeatureSaliencyMap = new HashMap<>();

        // process LIME results
        for (Map.Entry<String, Saliency> saliencyMap : limeResults.getSaliencies().entrySet()) {
            List<FeatureSaliency> featureSaliencies = new ArrayList<>();
            String outputName = saliencyMap.getKey();
            Saliency saliency = saliencyMap.getValue();
            for (FeatureImportance featureImportance : saliency.getPerFeatureImportance()) {
                FeatureSaliency featureSaliency = new FeatureSaliency();
                featureSaliency.setName(featureImportance.getFeature().getName());
                featureSaliency.setScore(featureImportance.getScore());
                featureSaliency.setConfidence(featureImportance.getConfidence());
                featureSaliencies.add(featureSaliency);
            }
            combinedFeatureSaliencyMap.put(outputName, featureSaliencies);
        }

        // process SHAP results
        for (Map.Entry<String, Saliency> saliencyMap : shapResults.getSaliencies().entrySet()) {
            List<FeatureSaliency> featureSaliencies = new ArrayList<>();
            String outputName = saliencyMap.getKey();
            Saliency saliency = saliencyMap.getValue();
            for (FeatureImportance featureImportance : saliency.getPerFeatureImportance()) {
                FeatureSaliency featureSaliency = new FeatureSaliency();
                featureSaliency.setName(featureImportance.getFeature().getName());
                featureSaliency.setScore(featureImportance.getScore());
                featureSaliency.setConfidence(featureImportance.getConfidence());
                featureSaliencies.add(featureSaliency);
            }
        }

        return combinedFeatureSaliencyMap;
    }


    }
    


}
