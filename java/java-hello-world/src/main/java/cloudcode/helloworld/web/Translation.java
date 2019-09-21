package cloudcode.helloworld.web;

public class Translation {
    private String english;
    private String german;

    public Translation(String english, String german) {
        this.english = english;
        this.german = german;
    }

    public String getEnglish() {
        return english;
    }

    public String getGerman() {
        return german;
    }
}
