package gov.nist.biometrics;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.FileUtils;
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

    public static void run(String inputFilename, String outputFilename) throws ParserConfigurationException, SAXException, IOException {

        //String filename = "/home/mccaffrey/biometrics/401/xml/AllFields401.xml";
        //String filename = "/home/mccaffrey/biometrics/SomeFieldsTransformed20190917.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(inputFilename));

        ArrayList<ContentRecordSummary> contentRecordSummarys = new ArrayList<>();

        Element nistBiometricInformationExchangePackage = doc.getDocumentElement();
        NodeList firstChildren = nistBiometricInformationExchangePackage.getChildNodes();

        for (int i = 0; i < firstChildren.getLength(); i++) {
            if (ContentRecordSummary.isContentRecordSummary(firstChildren.item(i))) {
                Element record = (Element) firstChildren.item(i);
                contentRecordSummarys.add(ContentRecordSummary.convertRecordToCRS(record));
            }
        }

        // /itl:NISTBiometricInformationExchangePackage/itl:PackageInformationRecord[1]/itl:Transaction[1]/itl:TransactionContentSummary[1]
        NodeList packageChildren = nistBiometricInformationExchangePackage.getChildNodes(); //.getElementsByTagNameNS("*","TransactionContentSummary");
        Element transactionContentSummary = null;
        for (int i = 0; i < packageChildren.getLength(); i++) {
            if (packageChildren.item(i) instanceof Element) {
                Element packageChild = (Element) packageChildren.item(i);
                if (packageChild.getTagName().endsWith("PackageInformationRecord")) {
                    NodeList pirChildren = packageChild.getChildNodes();
                    for (int j = 0; j < pirChildren.getLength(); j++) {
                        if (pirChildren.item(j) instanceof Element) {
                            Element pirChild = (Element) pirChildren.item(j);
                            if (pirChild.getTagName().endsWith("Transaction")) {
                                NodeList transactionChildren = pirChild.getChildNodes();
                                for (int k = 0; k < transactionChildren.getLength(); k++) {
                                    if (transactionChildren.item(k) instanceof Element) {
                                        Element transactionChild = (Element) transactionChildren.item(k);
                                        if (transactionChild.getTagName().endsWith("TransactionContentSummary")) {
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

        NodeList tcsChildren = transactionContentSummary.getChildNodes();
        for (int i = 0; i < tcsChildren.getLength(); i++) {
            if (tcsChildren.item(i) instanceof Element) {
                Element tcsChild = (Element) tcsChildren.item(i);
                if (tcsChild.getTagName().endsWith("ContentRecordSummary")) {
                    transactionContentSummary.removeChild(tcsChild);
                }
            }
        }

        for (int i = 0; i < contentRecordSummarys.size(); i++) {

            Element crsElement = contentRecordSummarys.get(i).toXml(doc);
            transactionContentSummary.appendChild(crsElement);

        }

        if (outputFilename == null || outputFilename.isEmpty()) {
            outputFilename = generateCurrentFilename();
        }
        FileUtils.writeStringToFile(new File(outputFilename), xmlToString(doc), Charset.defaultCharset());
        //   /itl:NISTBiometricInformationExchangePackage/itl:PackageInformationRecord[1]/itl:Transaction[1]/itl:TransactionContentSummary[1]

    }

    public static String generateCurrentFilename() {
        StringBuilder sb = new StringBuilder();
        sb.append("output");

        Calendar now = Calendar.getInstance();

        sb.append(now.get(Calendar.YEAR));
        sb.append(now.get(Calendar.MONTH));
        sb.append(now.get(Calendar.DATE));
        sb.append(now.get(Calendar.HOUR_OF_DAY));
        sb.append(now.get(Calendar.MINUTE));
        sb.append(now.get(Calendar.SECOND));
        sb.append(now.get(Calendar.MILLISECOND));
        sb.append(".xml");
        return sb.toString();
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

    public static void printUsage() {
        System.out.println("Usage: java -jar RegenerateTransactionContentSummary.jar [input filename] [(optional) output filename]");
        System.out.println("This utility rebuilds the TransactionContentSummary in an NIEM version 4 XML file");
        System.out.println("The filename of the XML file must be submitted as the first entry on the command-line.");
        System.out.println("If the output filename is specified, the utlity will output that, otherwise a default filename will be used.");
    }

    public static final void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String inputFilename = null;
        String outputFilename = null;

        try {
            inputFilename = args[0];
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            printUsage();
            return;
        }

        if (inputFilename.equalsIgnoreCase("--help")
                || inputFilename.equalsIgnoreCase("-help")
                || inputFilename.equalsIgnoreCase("-?")
                || inputFilename.equalsIgnoreCase("--?")) {
            printUsage();
            return;
        }

        try {
            outputFilename = args[1];
        } catch (ArrayIndexOutOfBoundsException aioobe) {

        }

        RegenerateTransactionContentSummary.run(inputFilename, outputFilename);
    }
}
