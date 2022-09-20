package cn.itfield.wxcc.controller;

import cn.itfield.wxcc.CourseDocRepository;
import cn.itfield.wxcc.config.HighlightResultMapper;
import cn.itfield.wxcc.doc.dto.CourseDocQuery;
import cn.itfield.wxcc.doc.vo.AggrTermsVo;
import cn.itfield.wxcc.domain.doc.CourceDoc;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchDocController {
    @Autowired
    private CourseDocRepository courseDocRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private HighlightResultMapper highlightResultMapper;

    @PostMapping("/saveBatch")
    public JsonResult coursesave(@RequestBody List<CourceDoc> courcedocs){
        courseDocRepository.saveAll(courcedocs);
        return JsonResult.me();
    }
    @PostMapping("/deletebatch")
    public JsonResult deletebatch(@RequestBody List<CourceDoc> courcedocs){
        courseDocRepository.deleteAll();
        return JsonResult.me();
    }
    @PostMapping("/queryCourse")
    public JsonResult queryCourse(@RequestBody CourseDocQuery query){
        System.out.println(query.getKeyword());
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //搜索参数
        if(StringUtils.hasText(query.getKeyword())){
            boolQueryBuilder.filter().add(QueryBuilders.matchQuery("name",query.getKeyword()));
        }
        //课程类型
        if(query.getCourseTypeId()!=null){
            boolQueryBuilder.filter().add(QueryBuilders.termQuery("courseTypeId",query.getCourseTypeId()));

        }
        //课程类型名称
        if(StringUtils.hasText(query.getCourseTypeName())){
            boolQueryBuilder.filter().add(QueryBuilders.termQuery("courseTypeName",query.getCourseTypeName()));

        }
        //课程等级
        if(query.getGradeId()!=null){
            boolQueryBuilder.filter().add(QueryBuilders.termQuery("gradeId",query.getGradeId()));

        }
        //课程名称
        if(StringUtils.hasText(query.getGradeName())){
            boolQueryBuilder.filter().add(QueryBuilders.termQuery("gradeName",query.getGradeName()));

        }
        //最小价格
        if(query.getPriceMin()!=null){
            boolQueryBuilder.filter().add(QueryBuilders.rangeQuery("priceMin").gte(query.getPriceMin()));

        }
        //最大价格
        if(query.getPriceMin()!=null){
            boolQueryBuilder.filter().add(QueryBuilders.rangeQuery("priceMax").lte(query.getPriceMax()));

        }
        //添加条件
        queryBuilder.withQuery(boolQueryBuilder);
        //分页条件
        PageRequest pageable = PageRequest.of(query.getPageNo() - 1, query.getPageSize());
        queryBuilder.withPageable(pageable);
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("id");
        if(StringUtils.hasText(query.getOrderField())){
            sortBuilder=SortBuilders.fieldSort(query.getOrderField());
        }
        //排序
        if("desc".equalsIgnoreCase(query.getOrderType())){
            sortBuilder.order(SortOrder.DESC);
        }
        //排序
        if("asc".equalsIgnoreCase(query.getOrderType())){
            sortBuilder.order(SortOrder.ASC);
        }
        //高亮
       HighlightBuilder.Field  highlightField = new HighlightBuilder.Field("name")
                .preTags("<span style='color:red'>")        //前标签
                .postTags("</span>");                       //后标签
        queryBuilder.withHighlightFields(highlightField);
        //添加聚合条件
        queryBuilder.addAggregation(AggregationBuilders
                .terms("courseTypeNameAgg")     //聚合条件起一个名字  通过此名称来获取结果
                .field("courseTypeName")	            //聚合的字段  分组的字段
                .order(BucketOrder.count(false)).size(20));//排序后
        queryBuilder.addAggregation(AggregationBuilders
                .terms("gradeNameAgg")     //聚合条件起一个名字  通过此名称来获取结果
                .field("gradeName")	            //聚合的字段  分组的字段
                .order(BucketOrder.count(false)).size(20));//排序后
        //获取参数
        AggregatedPage<CourceDoc> page = elasticsearchTemplate.queryForPage(queryBuilder.build(),
                CourceDoc.class, highlightResultMapper);
        /*Page<CourceDoc> page = courseDocRepository.search(queryBuilder.build());*/
        StringTerms courseTypeNameAgg = (StringTerms) page.getAggregation("courseTypeNameAgg");
        //AggrTermsVo自己写的
        List<AggrTermsVo> courseTypeNameAggr = courseTypeNameAgg.getBuckets().stream().map(bucket ->
                new AggrTermsVo(bucket.getKeyAsString(), bucket.getDocCount())
        ).collect(Collectors.toList());
        //遍历聚合结果
        StringTerms gradeNameAgg = (StringTerms) page.getAggregation("gradeNameAgg");
        List<AggrTermsVo> gradeNameAggr = gradeNameAgg.getBuckets().stream().map(bucket ->
                new AggrTermsVo(bucket.getKeyAsString(), bucket.getDocCount())
        ).collect(Collectors.toList());
        //封装参数
        PageList<CourceDoc> pageList = new PageList<>();
        pageList.setTotal(page.getTotalElements());
        pageList.setRows(page.getContent());
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("courseTypeNameAggr",courseTypeNameAggr);
        map.put("gradeNameAggr",gradeNameAggr);
        map.put("pageList",pageList);
        return JsonResult.me().setData(map);
    }
}
