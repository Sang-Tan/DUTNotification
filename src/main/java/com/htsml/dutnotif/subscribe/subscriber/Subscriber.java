package com.htsml.dutnotif.subscribe.subscriber;

import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnumConverter;
import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @Column(name = "type_id")
    @Convert(converter = SubscriberTypeEnumConverter.class)
    private SubscriberTypeEnum type;

    private String additionalInfo;

    @PostLoad
    public void postLoad() {
        this.type = SubscriberTypeEnum.valueOf(this.type.name());
    }
}
