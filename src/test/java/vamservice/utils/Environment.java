package vamservice.utils;

public interface Environment {

    String stage = "stage";
    String QA = "qa";
    String DEV = "dev";

    String healthStatusUrl = "https://o-dev-video-ads-module.ad-tech.nbcuni.com/v1/heartbeat";
}

