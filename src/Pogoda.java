import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pogoda {

    public static void main(String[] args) throws IOException {
        String vysokayaGora = "https://yandex.ru/pogoda/visokaya-gora";
        String cairo = "https://yandex.ru/pogoda/11485";
        String cuberPedi = "https://yandex.ru/pogoda/111097";
        String mekka = "https://yandex.ru/pogoda/41030";
        String istambul = "https://yandex.ru/pogoda/11508";
        String americanBich = "https://yandex.ru/pogoda/?lat=30.57460022&lon=-81.44509125";

        String[] urls = {vysokayaGora, cairo, cuberPedi, mekka, istambul, americanBich};
        String[] goroda = {"ВЫСОКАЯ ГОРА", "КАИР", "КУБЕР-ПЕДИ", "МЕККА", "ИСТАМБУЛ", "АМЕРИКАН-БИЧ"};
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < goroda.length; i++) {
            map.put(goroda[i], urls[i]);
        }

        String temperatura = "Текущая температура</span><span class=\"temp__value temp__value_with-unit\">.\\d+</span>";
        String yasnoIliKak = "day-anchor\":\\{\"anchor\":21}}'>[А-яA-z\\s]*</div>";
        String ochuchaetcaKak = "Ощущается как</div><div class=\"term__value\"><div class=\"temp\" role=\"text\"><span class=\"temp__value temp__value_with-unit\">.\\d+</span>";
        String vlazhnost = "Влажность: \\d+%";
        String davlenie = "Давление: \\d+ Миллиметров ртутного столба";
        String veter = "\"wind-speed\">\\d+.\\d+</span>";

        for (int i = 0; i < map.size(); i++) {
            StringBuilder sb = new StringBuilder();
            InputStream inputStream = new URL(map.get(goroda[i])).openStream();
            Scanner scanner = new Scanner(inputStream);

            while (scanner.hasNextLine())
                sb.append(scanner.nextLine());

            scanner.close();
            inputStream.close();

            System.out.println(goroda[i]);

            //ТЕМПЕРАТУРА
            Pattern temper = Pattern.compile(temperatura);
            Matcher temp = temper.matcher(sb.toString());

            //ЯСНО ИЛИ ОБЛАЧНО
            Pattern yasno = Pattern.compile(yasnoIliKak);
            Matcher yasn = yasno.matcher(sb.toString());


            //ОЩУЩАЕТСЯ КАК
            Pattern ochuch = Pattern.compile(ochuchaetcaKak);
            Matcher ochu = ochuch.matcher(sb.toString());

            //ВЛАЖНОСТЬ
            Pattern vlazhno = Pattern.compile(vlazhnost);
            Matcher vlazh = vlazhno.matcher(sb.toString());

            //ДАВЛЕНИЕ
            Pattern davlen = Pattern.compile(davlenie);
            Matcher davl = davlen.matcher(sb.toString());

            //VETER
            Pattern vete = Pattern.compile(veter);
            Matcher vet = vete.matcher(sb.toString());

            while (temp.find())
                System.out.print("Текущая температура: " + temp.group().replaceAll("Текущая температура</span><span class=\"temp__value temp__value_with-unit\">|</span>", ""));

            while (yasn.find())
                System.out.println(", " + yasn.group().replaceAll("day-anchor\":\\{\"anchor\":21}}'>|</div>", ""));

            while (ochu.find())
                System.out.println("Ощущается как: " + ochu.group().replaceAll("Ощущается как</div><div class=\"term__value\"><div class=\"temp\" role=\"text\"><span class=\"temp__value temp__value_with-unit\">|</span>", ""));

            while (vlazh.find())
                System.out.println(vlazh.group());

            while (davl.find())
                System.out.println(davl.group().replaceAll("Миллиметров ртутного столба", "мм рт. ст."));

            while (vet.find())
                System.out.println("Ветер: "+vet.group().replaceAll("\"wind-speed\">|</span>", "") + " м/с");

            System.out.println();
        }
    }
}