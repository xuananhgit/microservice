package com.example.userservice.config.sqlsource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Data
@Component
@ConfigurationProperties(prefix = "sqlgetter")
public class SqlGetter {
    private String filepath;
    private String id;
    private String databaseName;
    private String tagName;

    public String get(String idSql) {
        String value = "";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(filepath));

            NodeList nodeList = document.getElementsByTagName(tagName);

            CUC_CU : for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String id = node.getAttributes().getNamedItem(this.id).getNodeValue();
                if (id.equals(idSql)) {
                    NodeList nodeListChild = node.getChildNodes();
                    for (int j = 0; j < nodeListChild.getLength(); j++) {
                        Node nodeChild = nodeListChild.item(j);
                        if (databaseName.equals(nodeChild.getNodeName())) {
                            value = nodeChild.getTextContent();
                            break CUC_CU;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
