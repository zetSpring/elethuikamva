package elethu.ikamva.domain.enums;

public enum ReportType {
    INDIVIDUAL("Individual"),
    MONTHLY("Monthly"),
    SUMMARY("Summary"),
    YEARLY("Yearly"),
    CUSTOM("Custom"),
    ALL("All");

    public final String value;

    ReportType(String value) {
        this.value = value;
    }
}
