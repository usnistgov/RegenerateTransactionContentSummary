package gov.nist.biometrics;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
        
       // /itl:NISTBiometricInformationExchangePackage/itl:PackageInformationRecord[1]/itl:Transaction[1]/itl:TransactionContentSummary[1]
        NodeList packageChildren = nistBiometricInformationExchangePackage.getChildNodes(); //.getElementsByTagNameNS("*","TransactionContentSummary");
        Element transactionContentSummary = null;
        for(int i = 0; i < packageChildren.getLength(); i++) {
            if(packageChildren.item(i) instanceof Element) {
                Element packageChild = (Element) packageChildren.item(i);
                if(packageChild.getTagName().endsWith("PackageInformationRecord")) {
                    NodeList pirChildren = packageChild.getChildNodes();
                    for(int j = 0; j < pirChildren.getLength(); j++) {
                        if(pirChildren.item(j) instanceof Element) {
                            Element pirChild = (Element) pirChildren.item(j);
                            if(pirChild.getTagName().endsWith("Transaction")) {
                                NodeList transactionChildren = pirChild.getChildNodes();
                                for(int k = 0; k < transactionChildren.getLength(); k++) {
                                    if(transactionChildren.item(k) instanceof Element) {
                                        Element transactionChild = (Element) transactionChildren.item(k);
                                        if(transactionChild.getTagName().endsWith("TransactionContentSummary")) {
                                            transactionContentSummary = transactionChild;
                                        }
                                    }
                                }
                            }
                            
                        }                                                
                    }                                                            
                }
                
            }
        }
        
        
        System.out.println("transaction name = " + transactionContentSummary.getTagName());
        
        System.out.println(xmlToString(contentRecordSummarys.get(0).toXml(doc)));
        
        NodeList tcsChildren = transactionContentSummary.getChildNodes();
        for(int i = 0; i < tcsChildren.getLength(); i++) {
            if(tcsChildren.item(i) instanceof Element) {
                Element tcsChild = (Element) tcsChildren.item(i);
                if(tcsChild.getTagName().endsWith("ContentRecordSummary")) {
                    transactionContentSummary.removeChild(tcsChild);
                }
            }
        }
        
        for(int i = 0; i < contentRecordSummarys.size(); i++) {
            
            Element crsElement = contentRecordSummarys.get(i).toXml(doc);
            transactionContentSummary.appendChild(crsElement);
            
        }
        
        System.out.println(xmlToString(doc));
        
     //   /itl:NISTBiometricInformationExchangePackage/itl:PackageInformationRecord[1]/itl:Transaction[1]/itl:TransactionContentSummary[1]
        
    }
    
     public static String xmlToString(Node inputNode) {
        try {
            Source source = new DOMSource(inputNode);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    
    public static final void main(String [] args) throws ParserConfigurationException, IOException, SAXException {
        RegenerateTransactionContentSummary.run();
    }
}
