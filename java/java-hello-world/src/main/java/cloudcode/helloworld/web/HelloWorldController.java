package cloudcode.helloworld.web;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloWorldController {
    private Translate translate;

    /**
     * Takes in a json encoded string array to translate
     * returns an JSON array of the translated text.
     */
    @RequestMapping(value = "/", produces = "application/json")
    @ResponseBody
    public List<Translation> translate(@RequestParam String text) throws JSONException {
        // Instantiates a client
        translate = TranslateOptions.getDefaultInstance().getService();

        JSONArray jsonArray = new JSONArray(text);

        List<Translation> translations = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            translations.add(getTranslation(jsonArray.get(i).toString()));
        }

        return translations;
    }

    private Translation getTranslation(String text) {
        String englishTranslation, germanTranslation;

        String incomingLanguage = translate.detect(text).getLanguage();
        if (!incomingLanguage.equals("en")) {
            englishTranslation = translate.translate(
                    text,
                    Translate.TranslateOption.targetLanguage("en")).getTranslatedText();
        } else {
            englishTranslation = text;
        }

        germanTranslation =
                translate.translate(
                        text,
                        Translate.TranslateOption.targetLanguage("de")).getTranslatedText();

        System.out.printf("Source Language: %s%n", incomingLanguage);
        System.out.printf("English Translation: %s%n", englishTranslation);
        System.out.printf("German Translation: %s%n", germanTranslation);

        return new Translation(englishTranslation, germanTranslation);
    }
}
