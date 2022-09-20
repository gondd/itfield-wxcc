package cn.itfield.wxcc;

import cn.itfield.wxcc.domain.doc.CourceDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseDocRepository extends ElasticsearchRepository<CourceDoc,Long> {
}
