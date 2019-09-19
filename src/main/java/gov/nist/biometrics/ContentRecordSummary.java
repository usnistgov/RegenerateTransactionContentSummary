
package gov.nist.biometrics;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



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
    public static boolean isContentRecordSummary(Node node) {

        String name = node.getNodeName();
        if(name == null) return false;
        if(name.endsWith("PackageDescriptiveTextRecord") ||
                name.endsWith("PackageLegacyFingerprintImageRecord") ||
                name.endsWith("PackageUserDefinedImageRecord") ||
                name.endsWith("PackageSignatureImageRecord") ||
                name.endsWith("PackageMinutiaeRecord") ||
                name.endsWith("PackagePhotographicBodyPartImageRecord") ||
                name.endsWith("PackageVoiceDataRecord") ||
                name.endsWith("PackageForensicDentalDataRecord") ||
                name.endsWith("PackageLatentImageRecord") ||
                name.endsWith("PackageFingerImpressionImageRecord") ||
                name.endsWith("PackagePalmPrintImageRecord") ||
                name.endsWith("PackageUserDefinedTestingImageRecord") ||
                name.endsWith("PackageIrisImageRecord") ||
                name.endsWith("PackageDNARecord") ||
                name.endsWith("PackagePlantarImageRecord") ||
                name.endsWith("PackageSourceRepresentationRecord") ||
                name.endsWith("PackageAssociatedContextRecord") ||
                name.endsWith("PackageNonPhotographicImageryRecord") ||
                name.endsWith("PackageInformationAssuranceRecord") ||
                name.endsWith("PackageCBEFFBiometricDataRecord") )
            return true;

        return false;
    }
    
    Element toXml(Document doc) {
        
        Element crs = doc.createElementNS("http://biometrics.nist.gov/standard/2011", "ContentRecordSummary");
        Element id = doc.createElementNS("http://publication.niem.gov/niem/domains/biometrics/4.0/1/", "ImageReferenceID");
        Element code = doc.createElementNS("http://biometrics.nist.gov/standard/2011", "RecordCategoryCode");
        crs.setPrefix("itl");
        id.setPrefix("biom");
        code.setPrefix("itl");
        id.setTextContent(this.getImageReferenceID());
        code.setTextContent(this.getRecordCategoryCode());
        crs.appendChild(id);
        crs.appendChild(code);
        
        return crs;
    }
    
    static ContentRecordSummary convertRecordToCRS(Element record) {
        ContentRecordSummary crs = new ContentRecordSummary();
        
        String recordName = record.getTagName();

        if(recordName.endsWith("PackageDescriptiveTextRecord")) crs.setRecordCategoryCode("02"); else if
            (recordName.endsWith("PackageLegacyFingerprintImageRecord")) crs.setRecordCategoryCode("04"); else if
            (recordName.endsWith("PackageUserDefinedImageRecord")) crs.setRecordCategoryCode("07"); else if
            (recordName.endsWith("PackageSignatureImageRecord")) crs.setRecordCategoryCode("08"); else if
            (recordName.endsWith("PackageMinutiaeRecord")) crs.setRecordCategoryCode("09"); else if
            (recordName.endsWith("PackagePhotographicBodyPartImageRecord")) crs.setRecordCategoryCode("10"); else if
            (recordName.endsWith("PackageVoiceDataRecord")) crs.setRecordCategoryCode("11"); else if                                                                                
            (recordName.endsWith("PackageForensicDentalDataRecord")) crs.setRecordCategoryCode("12"); else if
            (recordName.endsWith("PackageLatentImageRecord")) crs.setRecordCategoryCode("13"); else if
            (recordName.endsWith("PackageFingerImpressionImageRecord")) crs.setRecordCategoryCode("14"); else if
            (recordName.endsWith("PackagePalmPrintImageRecord")) crs.setRecordCategoryCode("15"); else if
            (recordName.endsWith("PackageUserDefinedTestingImageRecord")) crs.setRecordCategoryCode("16"); else if
            (recordName.endsWith("PackageIrisImageRecord")) crs.setRecordCategoryCode("17"); else if
            (recordName.endsWith("PackageDNARecord")) crs.setRecordCategoryCode("18"); else if
            (recordName.endsWith("PackagePlantarImageRecord")) crs.setRecordCategoryCode("19"); else if
            (recordName.endsWith("PackageSourceRepresentationRecord")) crs.setRecordCategoryCode("20"); else if
            (recordName.endsWith("PackageAssociatedContextRecord")) crs.setRecordCategoryCode("21"); else if
            (recordName.endsWith("PackageNonPhotographicImageryRecord")) crs.setRecordCategoryCode("22"); else if
            (recordName.endsWith("PackageInformationAssuranceRecord")) crs.setRecordCategoryCode("98"); else if
            (recordName.endsWith("PackageCBEFFBiometricDataRecord")) crs.setRecordCategoryCode("99");            
        

        NodeList children = record.getChildNodes();
        for(int i = 0; i < children.getLength(); i++) {
            if(children.item(i).getNodeName().endsWith("ImageReferenceID")) {
                Node id = children.item(i);
                Element idElement = (Element) id;            
                String idValue = idElement.getTextContent();
                
                System.out.println("ID = " + idValue);
                crs.setImageReferenceID(idValue);
                    
            }
        }

        return crs;
    }
    
}
