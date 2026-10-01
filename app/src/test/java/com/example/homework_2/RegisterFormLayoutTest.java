package com.example.homework_2;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.*;

public class RegisterFormLayoutTest {

    private static final String LAYOUT =
            "src/main/res/layout/activity_registerform.xml";
    private static Document doc;

    @BeforeClass
    public static void parse() throws Exception {
        File f = new File(LAYOUT);
        assertTrue("Layout file missing: " + f.getAbsolutePath(), f.exists());
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        fac.setNamespaceAware(true);
        doc = fac.newDocumentBuilder().parse(f);
    }

    private static Element byId(String id) {
        NodeList all = doc.getElementsByTagName("*");
        for (int i = 0; i < all.getLength(); i++) {
            Element e = (Element) all.item(i);
            String v = e.getAttributeNS(
                    "http://schemas.android.com/apk/res/android", "id");
            if (("@+id/" + id).equals(v) || ("@id/" + id).equals(v)) return e;
        }
        return null;
    }

    @Test
    public void T01_layoutFileExists() {
        assertNotNull(doc);
    }

    @Test
    public void T02_hasFourEditTextsWithCorrectIds() {
        for (String id : new String[]{"etUsername", "etPassword", "etRetype", "etBirthdate"}) {
            Element e = byId(id);
            assertNotNull("Missing id: " + id, e);
            assertTrue(id + " must be an EditText",
                    e.getTagName().endsWith("EditText"));
        }
    }

    @Test
    public void T03_passwordFieldsAreHidden() {
        for (String id : new String[]{"etPassword", "etRetype"}) {
            Element e = byId(id);
            assertNotNull(e);
            String type = e.getAttributeNS(
                    "http://schemas.android.com/apk/res/android", "inputType");
            assertTrue(id + " must be textPassword", type.contains("textPassword"));
        }
    }

    @Test
    public void T04_birthdateFieldIsNotPassword() {
        Element e = byId("etBirthdate");
        assertNotNull(e);
        String type = e.getAttributeNS(
                "http://schemas.android.com/apk/res/android", "inputType");
        assertFalse("etBirthdate must not be textPassword", type.contains("textPassword"));
    }

    @Test
    public void T05_selectButtonExistsNextToBirthdate() {
        Element btn = byId("btnSelect");
        assertNotNull("Missing id: btnSelect", btn);
        assertTrue("btnSelect must be a Button or MaterialButton", btn.getTagName().equals("Button") || btn.getTagName().equals("com.google.android.material.button.MaterialButton"));
        // Có thể kiểm tra cùng parent với etBirthdate nếu cần
        Element et = byId("etBirthdate");
        assertNotNull(et);
        assertEquals("btnSelect and etBirthdate must have the same parent (or grandparent if wrapped in TextInputLayout)",
                btn.getParentNode(), et.getParentNode().getParentNode());
    }

    @Test
    public void T06_genderRadioGroupStructure() {
        Element rg = byId("rgGender");
        assertNotNull("Missing id: rgGender", rg);
        assertEquals("rgGender must be a RadioGroup", "RadioGroup", rg.getTagName());
        Element rbMale = byId("rbMale");
        Element rbFemale = byId("rbFemale");
        assertNotNull("Missing id: rbMale", rbMale);
        assertNotNull("Missing id: rbFemale", rbFemale);
        assertEquals("rbMale must be a RadioButton", "RadioButton", rbMale.getTagName());
        assertEquals("rbFemale must be a RadioButton", "RadioButton", rbFemale.getTagName());
        assertEquals("rbMale must be child of rgGender", rg, rbMale.getParentNode());
        assertEquals("rbFemale must be child of rgGender", rg, rbFemale.getParentNode());
    }

    @Test
    public void T07_radioTextsCorrect() {
        Element rbMale = byId("rbMale");
        assertNotNull(rbMale);
        String textMale = rbMale.getAttributeNS(
                "http://schemas.android.com/apk/res/android", "text");
        assertTrue("rbMale text must be Male or a string reference", textMale.equals("Male") || textMale.startsWith("@string/"));

        Element rbFemale = byId("rbFemale");
        assertNotNull(rbFemale);
        String textFemale = rbFemale.getAttributeNS(
                "http://schemas.android.com/apk/res/android", "text");
        assertTrue("rbFemale text must be Female or a string reference", textFemale.equals("Female") || textFemale.startsWith("@string/"));
    }

    @Test
    public void T08_hasThreeCheckBoxes() {
        for (String id : new String[]{"cbTennis", "cbFutbal", "cbOthers"}) {
            Element e = byId(id);
            assertNotNull("Missing id: " + id, e);
            assertEquals(id + " must be a CheckBox", "CheckBox", e.getTagName());
        }
    }

    @Test
    public void T09_hasResetAndSignUpButtons() {
        Element btnReset = byId("btnReset");
        assertNotNull("Missing id: btnReset", btnReset);
        assertEquals("btnReset must be a Button", "com.google.android.material.button.MaterialButton", btnReset.getTagName());

        Element btnSignUp = byId("btnSignUp");
        assertNotNull("Missing id: btnSignUp", btnSignUp);
        assertEquals("btnSignUp must be a Button", "com.google.android.material.button.MaterialButton", btnSignUp.getTagName());
    }

    @Test
    public void T10_idsAreUnique() {
        Set<String> ids = new HashSet<>();
        NodeList all = doc.getElementsByTagName("*");
        for (int i = 0; i < all.getLength(); i++) {
            Element e = (Element) all.item(i);
            String v = e.getAttributeNS(
                    "http://schemas.android.com/apk/res/android", "id");
            if (!v.isEmpty()) {
                assertFalse("Duplicate ID found: " + v, ids.contains(v));
                ids.add(v);
            }
        }
    }

    @Test
    public void T11_noHardcodedColors() {
        NodeList all = doc.getElementsByTagName("*");
        for (int i = 0; i < all.getLength(); i++) {
            Element e = (Element) all.item(i);
            NamedNodeMap attrs = e.getAttributes();
            for (int j = 0; j < attrs.getLength(); j++) {
                Node attr = attrs.item(j);
                String val = attr.getNodeValue();
                if (attr.getNodeName().contains("color") || attr.getNodeName().contains("background") || attr.getNodeName().contains("tint")) {
                    assertFalse("Hardcoded color found in " + e.getTagName() + " attribute " + attr.getNodeName() + " = " + val, val.startsWith("#"));
                }
            }
        }
    }

    @Test
    public void T12_usesThemeColors() {
        boolean usesBgMain = false;
        boolean usesSurfaceCard = false;
        boolean usesPrimary = false;
        boolean usesTextPrimary = false;

        NodeList all = doc.getElementsByTagName("*");
        for (int i = 0; i < all.getLength(); i++) {
            Element e = (Element) all.item(i);
            NamedNodeMap attrs = e.getAttributes();
            for (int j = 0; j < attrs.getLength(); j++) {
                Node attr = attrs.item(j);
                String val = attr.getNodeValue();
                if (val.contains("@color/bg_main")) usesBgMain = true;
                if (val.contains("@color/surface_card")) usesSurfaceCard = true;
                if (val.contains("@color/primary")) usesPrimary = true;
                if (val.contains("@color/text_primary")) usesTextPrimary = true;
            }
        }

        assertTrue("Layout must use @color/bg_main", usesBgMain);
        assertTrue("Layout must use @color/surface_card", usesSurfaceCard);
        assertTrue("Layout must use @color/primary", usesPrimary);
        assertTrue("Layout must use @color/text_primary", usesTextPrimary);
    }

    @Test
    public void T14_rootIsScrollable() {
        Element root = doc.getDocumentElement();
        assertTrue("Root must be scrollable (ScrollView or NestedScrollView)",
                root.getTagName().contains("ScrollView"));
    }
}
