package cn.nbmly.springbook.mapper;

import cn.nbmly.springbook.po.Publisher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PublisherMapper {
    Publisher findPublisherByPubId(int pubId);

    List<Publisher> findPublishers();

    int addPublisher(Publisher publisher);

    int editPublisher(Publisher publisher);

    int deletePublisher(int pubId);
}
