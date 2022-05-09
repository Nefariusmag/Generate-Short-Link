package com.yourcodereview.generateshortlinks.generatelinks;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIT {
    public static final String ORIGINAL_LINK = "https://yandex.ru/maps/geo/zarevy_proyezd_10_podyezd_8/1955428351/?ll=37.659658%2C55.885913&mode=search&sctx=ZAAAAAgAEAAaKAoSCZylZDkJm0BAET9YxoZu7E5AEhIJAAAAAIABtT8RAPjGoAkdnT8iBgABAgMEBSgKOABAgYcGSAFqAnJ1nQHNzEw9oAEAqAEAvQGLndzpwgEBAOoBAPIBAPgBAIICHtC30LDRgNC10LLRi9C5INC%2F0YDQvtC10LfQtCAxMIoCAJICAJoCDGRlc2t0b3AtbWFwcw%3D%3D&sll=37.658365%2C55.886029&sspn=0.011224%2C0.004626&text=%D0%B7%D0%B0%D1%80%D0%B5%D0%B2%D1%8B%D0%B9%20%D0%BF%D1%80%D0%BE%D0%B5%D0%B7%D0%B4%2010&utm_source=main_stripe_big&z=18.88";
    public static final String SHORT_LINK = "1649951760544092";

    public static final String SECOND_ORIGINAL_LINK = "https://www.canva.com/design/DAFAHrXLNFg/_jSSh8z-CE-0ZxaNx9GNgA/edit";
    public static final String SECOND_SHORT_LINK = "1649951760545736";

    public static final String FAKE_SHORT_LINK = "1649951760541111";

    public static final String FRESH_ORIGINAL_LINK = "https://www.youtube.com/watch?v=rYfarA6_5Eo";
    public static final String FRESH_SHORT_LINK = "1649951760548231";
}
