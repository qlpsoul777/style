package com.qlp.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2014/9/5.
 */
public class LuceneBean {
    private Logger logger = LoggerFactory.getLogger(LuceneBean.class);
    private String indexPath;  //索引存放路径
    private Analyzer analyzer;  //分词器
    private Directory directory;  //索引目录
    private IndexWriterConfig iwc;

    //初始化方法
    public void init(){
        try {
            this.analyzer = new IKAnalyzer();
            this.directory = FSDirectory.open(new File(this.indexPath));
            this.iwc = new IndexWriterConfig(Version.LATEST,this.analyzer);
            this.iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        } catch (IOException e) {
            logger.error("lucene初始化失败", e.getMessage());
        }
    }

    //创建索引
    public Boolean createIndex(Document document){
        IndexWriter iw = null;
        try {
            iw = new IndexWriter(this.directory,this.iwc);
            iw.addDocument(document);
            iw.commit();
        } catch (IOException e) {
            logger.error("lucene创建索引失败", e.getMessage());
            return Boolean.FALSE;
        }finally {
            if(iw != null){
                try {
                    iw.close();
                } catch (IOException e) {
                    logger.error("lucene创建索引后关闭IndexWriter失败", e.getMessage());
                }
            }
        }
        return Boolean.TRUE;
    }

    //修改索引
    public Boolean updateIndex(String id,Document document){
        IndexWriter iw = null;
        try {
            iw = new IndexWriter(this.directory,this.iwc);
            iw.updateDocument(new Term("id",id),document);
            iw.commit();
        } catch (IOException e) {
            logger.error("lucene修改索引失败", e.getMessage());
            return Boolean.FALSE;
        }finally {
            if(iw != null){
                try {
                    iw.close();
                } catch (IOException e) {
                    logger.error("lucene修改索引后关闭IndexWriter失败", e.getMessage());
                }
            }
        }
        return Boolean.TRUE;
    }

    //删除索引
    public Boolean deleteIndex(String id){
        IndexWriter iw = null;
        try {
            iw = new IndexWriter(this.directory,this.iwc);
            iw.deleteDocuments(new Term("id",id));
            iw.commit();
        } catch (IOException e) {
            logger.error("lucene删除索引失败", e.getMessage());
            return Boolean.FALSE;
        }finally {
            if(iw != null){
                try {
                    iw.close();
                } catch (IOException e) {
                    logger.error("lucene删除索引后关闭IndexWriter失败", e.getMessage());
                }
            }
        }
        return Boolean.TRUE;
    }

    //搜索结果中keyWord高亮显示
    public String highlightKeyWord(String fieldName,String text,Query query){
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style=\"color:#FF0000\">","</span>");
        QueryScorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter,scorer);
        highlighter.setTextFragmenter(new SimpleFragmenter());
        try {
            return highlighter.getBestFragment(this.analyzer,fieldName,text);
        } catch (IOException e) {
            logger.error("keyWord高亮失败", e.getMessage());
        } catch (InvalidTokenOffsetsException e) {
            logger.error("keyWord高亮失败", e.getMessage());
        }
        return "";
    }

    //分页查询结果
    public Page<Map<String,String>> search(Query query,Pageable pageable){
        Page<Map<String, String>> page = null;
        IndexReader reader = null;
        try {
            reader = DirectoryReader.open(this.directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs results = searcher.search(query, pageable.getOffset() + pageable.getPageSize());
            ScoreDoc[] hits = results.scoreDocs;
            int numTotalHits = results.totalHits;
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (int i = pageable.getOffset(); i < Math.min(pageable.getOffset() + pageable.getPageSize(), hits.length); i++) {
                Document document = searcher.doc(hits[i].doc);
                List<IndexableField> indexableFields = document.getFields();
                Map<String, String> record = new HashMap<String, String>();
                for (IndexableField indexableField : indexableFields) {
                    String name = indexableField.name();
                    String value = indexableField.stringValue();
                    if ((name != null) && (value != null)) {
                        String highlighter = highlightKeyWord(name, value, query);
                        if (StringUtils.isEmpty(highlighter)) {
                            highlighter = value;
                        }
                        record.put(name, highlighter);
                    }
                }
                list.add(record);
            }
            page = new PageImpl<Map<String, String>>(list, pageable, numTotalHits);
        } catch (IOException e) {
            logger.error("搜索失败",e.getMessage());
        }finally {
            if(reader!= null){
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error("搜索完成后关闭IndexReader失败",e.getMessage());
                }
            }
        }
        return page;
    }

    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }
}
