package ug.co.absa.paybill.domain.enumeration;

/**
 * This enum shows all available payment channels\n@author Banada Ismael ABSA DT team
 */
public enum PaymentChannel {
    OVERTHECOUNTER("COUNTER"),
    ABSAINTERNETBANKING("INTERNETBANKING"),
    POINTOFSALE("POS"),
    MOBILEAPP("APP"),
    CHATBOT("ABBY"),
    USSD;

    private final String value;

    PaymentChannel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
