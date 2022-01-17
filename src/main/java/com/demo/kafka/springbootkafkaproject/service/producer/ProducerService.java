package com.demo.kafka.springbootkafkaproject.service.producer;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProducerService {

    @Value("${spring.kafka.topic}")
    private String kafkaUsersTopic;

    private final Logger logger;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ProducerService(Logger logger) {
        this.logger = logger;
    }

    public void sendMessage(String message) {
        Date now = new Date();
        logger.info(String.format(logProduceMessage(), message));
        this.kafkaTemplate.send(kafkaUsersTopic, "Message: " + message + "\nTimestamp Produced: " + getSecondsStringFromDate(now));
    }

    public void sendMessages() {
        Date now = new Date();
        String mockOrder = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel pharetra vel turpis nunc eget lorem dolor. Tristique risus nec feugiat in. Suspendisse sed nisi lacus sed viverra tellus in. Commodo elit at imperdiet dui accumsan sit. In tellus integer feugiat scelerisque. Nunc lobortis mattis aliquam faucibus purus in massa tempor nec. Sed pulvinar proin gravida hendrerit lectus. Sit amet facilisis magna etiam tempor orci eu lobortis elementum. Amet aliquam id diam maecenas ultricies mi eget. Vitae semper quis lectus nulla at. Eu ultrices vitae auctor eu augue ut lectus. Pulvinar neque laoreet suspendisse interdum consectetur. In vitae turpis massa sed elementum tempus. Nisl vel pretium lectus quam id leo in vitae turpis. Nibh nisl condimentum id venenatis a condimentum vitae sapien pellentesque. Senectus et netus et malesuada fames ac turpis egestas. Nullam ac tortor vitae purus faucibus ornare suspendisse sed. Rutrum tellus pellentesque eu tincidunt. Mi eget mauris pharetra et ultrices neque. Aliquam nulla facilisi cras fermentum odio. Ut morbi tincidunt augue interdum velit euismod in pellentesque. Porta non pulvinar neque laoreet suspendisse interdum consectetur libero id. Magna etiam tempor orci eu lobortis elementum. Mattis aliquam faucibus purus in massa tempor nec feugiat. Tellus pellentesque eu tincidunt tortor. Dolor sed viverra ipsum nunc aliquet. Lacus vestibulum sed arcu non odio euismod lacinia at. Auctor eu augue ut lectus arcu bibendum at varius. Senectus et netus et malesuada fames ac turpis. Nisi vitae suscipit tellus mauris. Imperdiet sed euismod nisi porta lorem mollis. Nunc aliquet bibendum enim facilisis gravida neque convallis a. Morbi tristique senectus et netus et. Dictum fusce ut placerat orci nulla pellentesque dignissim. At ultrices mi tempus imperdiet. Dui id ornare arcu odio ut sem nulla pharetra. Nullam ac tortor vitae purus. Aliquet lectus proin nibh nisl condimentum id venenatis a condimentum. Suspendisse faucibus interdum posuere lorem ipsum dolor. Eu non diam phasellus vestibulum. At risus viverra adipiscing at in tellus integer feugiat scelerisque. Leo integer malesuada nunc vel risus commodo viverra maecenas accumsan. Aliquam ut porttitor leo a diam sollicitudin tempor id eu. Interdum varius sit amet mattis vulputate enim nulla. A pellentesque sit amet porttitor eget dolor morbi. Pretium viverra suspendisse potenti nullam ac tortor vitae. Cras semper auctor neque vitae tempus. Porttitor rhoncus dolor purus non enim praesent. At tellus at urna condimentum mattis pellentesque id. Elit pellentesque habitant morbi tristique senectus et. Scelerisque fermentum dui faucibus in ornare quam viverra. Diam ut venenatis tellus in metus vulputate eu. Ipsum dolor sit amet consectetur adipiscing elit ut aliquam purus. Lectus vestibulum mattis ullamcorper velit sed. Lectus nulla at volutpat diam ut venenatis tellus in. Diam ut venenatis tellus in metus vulputate. Sapien pellentesque habitant morbi tristique. Aliquet sagittis id consectetur purus ut faucibus pulvinar elementum integer. Accumsan sit amet nulla facilisi morbi tempus iaculis urna id. Elit pellentesque habitant morbi tristique senectus et netus et malesuada. Mauris augue neque gravida in fermentum et. Et sollicitudin ac orci phasellus. Dignissim enim sit amet venenatis urna cursus eget. Nulla pellentesque dignissim enim sit amet venenatis urna cursus. Facilisis volutpat est velit egestas. Mauris rhoncus aenean vel elit. Tincidunt augue interdum velit euismod. Tincidunt dui ut ornare lectus sit amet est placerat in. Suspendisse in est ante in. Etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum. Ultricies leo integer malesuada nunc vel risus commodo. Viverra tellus in hac habitasse platea dictumst. Euismod elementum nisi quis eleifend quam adipiscing vitae proin. Massa ultricies mi quis hendrerit dolor magna eget. Turpis massa tincidunt dui ut ornare lectus sit amet. Volutpat ac tincidunt vitae semper quis lectus nulla at volutpat. Malesuada fames ac turpis egestas sed. Bibendum enim facilisis gravida neque convallis a cras. Est sit amet facilisis magna etiam tempor orci eu lobortis. Sed augue lacus viverra vitae. Sed libero enim sed faucibus. Ultrices in iaculis nunc sed augue. Volutpat est velit egestas dui id ornare arcu odio. Odio pellentesque diam volutpat commodo sed egestas egestas fringilla. Odio tempor orci dapibus ultrices in iaculis nunc. Non odio euismod lacinia at quis risus sed vulputate. Vitae auctor eu augue ut lectus arcu bibendum at varius. A diam sollicitudin tempor id. Massa sed elementum tempus egestas sed. Amet consectetur adipiscing elit pellentesque. Sit amet justo donec enim. Faucibus scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl purus in mollis nunc sed id. Arcu cursus euismod quis viverra nibh cras pulvinar mattis nunc. Mauris a diam maecenas sed enim ut sem viverra aliquet. Scelerisque varius morbi enim nunc faucibus a pellentesque sit amet. Volutpat commodo sed egestas egestas. Sed elementum tempus egestas sed sed. Odio morbi quis commodo odio aenean sed adipiscing diam. Id leo in vitae turpis massa sed. Vivamus arcu felis bibendum ut tristique et egestas quis. Mauris in aliquam sem fringilla ut morbi tincidunt augue. Mi ipsum faucibus vitae aliquet nec ullamcorper sit. Justo eget magna fermentum iaculis eu non diam. Sit amet consectetur adipiscing elit ut. Velit scelerisque in dictum non consectetur a. Lorem ipsum dolor sit amet. Urna porttitor rhoncus dolor purus non enim praesent elementum facilisis. Dui ut ornare lectus sit amet est placerat in. Felis imperdiet proin fermentum leo vel orci porta non. Cras semper auctor neque vitae. Sit amet purus gravida quis blandit turpis cursus. Eget dolor morbi non arcu risus quis varius. Duis ut diam quam nulla porttitor massa id neque. Sit amet consectetur adipiscing elit. Libero volutpat sed cras ornare arcu dui vivamus arcu felis. Cras ornare arcu dui vivamus arcu felis bibendum ut tristique. In nibh mauris cursus mattis molestie a iaculis at. Blandit turpis cursus in hac habitasse platea dictumst quisque. Amet nisl purus in mollis. Vitae justo eget magna fermentum iaculis eu non. Leo urna molestie at elementum eu facilisis sed. Nunc pulvinar sapien et ligula ullamcorper malesuada proin. Enim diam vulputate ut pharetra sit. Tellus cras adipiscing enim eu turpis egestas pretium. Pellentesque dignissim enim sit amet venenatis urna. Libero volutpat sed cras ornare arcu dui vivamus arcu. Molestie at elementum eu facilisis sed odio morbi quis commodo. Erat nam at lectus urna. Mattis ullamcorper velit sed ullamcorper morbi tincidunt. Amet luctus venenatis lectus magna fringilla urna porttitor rhoncus dolor. Tincidunt nunc pulvinar sapien et ligula ullamcorper malesuada proin. Dolor magna eget est lorem ipsum dolor sit amet consectetur. Libero volutpat sed cras ornare. Massa tincidunt dui ut ornare lectus sit amet est. Volutpat maecenas volutpat blandit aliquam etiam erat velit. Purus sit amet luctus venenatis lectus magna fringilla urna. Aliquam sem et tortor consequat id. Augue eget arcu dictum varius duis at consectetur lorem. Non nisi est sit amet. Accumsan sit amet nulla facilisi morbi tempus iaculis urna.";
        logger.info(String.format(logProduceMessage(), mockOrder));
        for(int i = 0; i < 100; i++) {
            this.kafkaTemplate.send(kafkaUsersTopic, "Message " + i + ": " + mockOrder + "\nTimestamp Produced: " + getSecondsStringFromDate(now));
        }
    }

    private String logProduceMessage() {
        Date now = new Date();
        return "\nTimestamp Produced: " + now.toInstant().toString() + "\nProducing message -> %s";
    }

    private String getSecondsStringFromDate(Date date) {
        return  date.toInstant().toString().substring(14, 23);
    }
}
