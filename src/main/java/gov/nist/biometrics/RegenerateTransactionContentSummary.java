package gov.nist.biometrics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author mccaffrey
 */
public class RegenerateTransactionContentSummary {

    public static void run() throws ParserConfigurationException, SAXException, IOException {

        String filename = "/home/mccaffrey/biometrics/401/xml/AllFields401.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(filename));

        ArrayList<ContentRecordSummary> contentRecordSummarys = new ArrayList<>();
        
        Element nistBiometricInformationExchangePackage = doc.getDocumentElement();                
        NodeList firstChildren = nistBiometricInformationExchangePackage.getChildNodes();
        for(int i = 0; i < firstChildren.getLength(); i++) {
            if(ContentRecordSummary.isContentRecordSummary(firstChildren.item(i))) {
               Element record = (Element) firstChildren.item(i);
                contentRecordSummarys.add(ContentRecordSummary.convertRecordToCRS(record));
            }
        }
        
        
     //   /itl:NISTBiometricInformationExchangePackage/itl:PackageInformationRecord[1]/itl:Transaction[1]/itl:TransactionContentSummary[1]
        
    }
    public static final void main(String [] args) throws ParserConfigurationException, IOException, SAXException {
        RegenerateTransactionContentSummary.run();
    }
}
