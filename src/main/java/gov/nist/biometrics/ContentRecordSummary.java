
package gov.nist.biometrics;

/**
 *
 * @author mccaffrey
 */
public class ContentRecordSummary {
    
    private String imageReferenceID = null;
    private String recordCategoryCode = null;

    /**
     * @return the imageReferenceID
     */
    public String getImageReferenceID() {
        return imageReferenceID;
    }

    /**
     * @param imageReferenceID the imageReferenceID to set
     */
    public void setImageReferenceID(String imageReferenceID) {
        this.imageReferenceID = imageReferenceID;
    }

    /**
     * @return the recordCategoryCode
     */
    public String getRecordCategoryCode() {
        return recordCategoryCode;
    }

    /**
     * @param recordCategoryCode the recordCategoryCode to set
     */
    public void setRecordCategoryCode(String recordCategoryCode) {
        this.recordCategoryCode = recordCategoryCode;
    }
    
}
