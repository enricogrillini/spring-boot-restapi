package it.eg.cookbook.service;

import it.eg.cookbook.model.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class DocumentServices implements InitializingBean {

    private Map<Integer, Document> map;

    @Override
    public void afterPropertiesSet() throws Exception {
        map = new LinkedHashMap<>();

        save(new Document(1, "Contratto", "Contratto tra le parti per sottoscrizione conto corrente"));
        save(new Document(2, "Recesso", "Norme per il recesso"));
        save(new Document(3, "Appendice", "Appendice al contratto di sottoscrizione"));
    }

    /**
     * Ritorna la lista documenti
     *
     * @return
     */
    public List<Document> getDocuments() {
        return Collections.unmodifiableList(new ArrayList<>(map.values()));
    }

    /**
     * Ritorna un singolo documento
     *
     * @param documentId
     * @return
     */
    public Document getDocument(Integer documentId) {
        return map.get(documentId);
    }

    /**
     * Elimina un documento
     *
     * @param documentId
     */
    public void delete(Integer documentId) {
        map.remove(documentId);
    }

    /**
     * Aggiorna o inserisce un documento
     *
     * @param document
     */
    public void save(Document document) {
        map.put(document.getId(), document);
    }

}

