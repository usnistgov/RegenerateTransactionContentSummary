/*

This software re-creates the Transaction Content Summary of a NIEM4 XML file.

This software was developed by employees of the National Institute of Standards
and Technology (NIST), an agency of the Federal Government and is being made 
available as a public service. Pursuant to title 17 United States Code Section 
105, works of NIST employees are not subject to copyright protection in the 
United States.  This software may be subject to foreign copyright.  Permission
in the United States and in foreign countries, to the extent that NIST may hold
copyright, to use, copy, modify, create derivative works, and distribute this 
software and its documentation without fee is hereby granted on a non-exclusive
basis, provided that this notice and disclaimer of warranty appears in all
copies. 

THE SOFTWARE IS PROVIDED 'AS IS' WITHOUT ANY WARRANTY OF ANY KIND, EITHER
EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT LIMITED TO, ANY WARRANTY
THAT THE SOFTWARE WILL CONFORM TO SPECIFICATIONS, ANY IMPLIED WARRANTIES OF 
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND FREEDOM FROM 
INFRINGEMENT, AND ANY WARRANTY THAT THE DOCUMENTATION WILL CONFORM TO THE 
SOFTWARE, OR ANY WARRANTY THAT THE SOFTWARE WILL BE ERROR FREE.  IN NO EVENT
SHALL NIST BE LIABLE FOR ANY DAMAGES, INCLUDING, BUT NOT LIMITED TO, DIRECT, 
INDIRECT, SPECIAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF, RESULTING FROM, OR 
IN ANY WAY CONNECTED WITH THIS SOFTWARE, WHETHER OR NOT BASED UPON WARRANTY, 
CONTRACT, TORT, OR OTHERWISE, WHETHER OR NOT INJURY WAS SUSTAINED BY PERSONS OR
PROPERTY OR OTHERWISE, AND WHETHER OR NOT LOSS WAS SUSTAINED FROM, OR AROSE OUT
OF THE RESULTS OF, OR USE OF, THE SOFTWARE OR SERVICES PROVIDED HEREUNDER.

*/
package gov.nist.biometrics;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
    
    public Element toXml(Document doc) {
        
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
    
    public static ContentRecordSummary convertRecordToCRS(Element record) {
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
                crs.setImageReferenceID(idValue);                    
            }
        }

        return crs;
    }
    
}
