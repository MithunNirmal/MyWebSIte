package com.mithunnirmal.merch.event.listener;

import com.mithunnirmal.merch.entities.Album;
import com.mithunnirmal.merch.entities.Order;
import com.mithunnirmal.merch.entities.OrderContent;
import com.mithunnirmal.merch.entities.Song;
import com.mithunnirmal.merch.enums.ProductType;
import com.mithunnirmal.merch.event.NewOrderPlacedEvent;
import com.mithunnirmal.merch.repositories.AlbumRepository;
import com.mithunnirmal.merch.repositories.OrderRepository;
import com.mithunnirmal.merch.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mithunnirmal.merch.utils.Final.*;

@Component
@Slf4j
public class NewOrderPlacementListener implements ApplicationListener<NewOrderPlacedEvent> {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private EmailService emailService;


    @Override
    public void onApplicationEvent(NewOrderPlacedEvent event) {
        log.info("Entering NewOrderPlacementEvent");
        Order order = event.getOrder();

        List<OrderContent> orderContents = order.getOrderContents();
        if(orderContents == null)
            return;

        String ref = "<p>Hello,</p>" +
                        "<p>Click  " +
                            "<a href=\"https://example.com\">here</a> to visit our website.</p>";



        String orderEmailContent = //"<div>" +
                "<p><b>Order Id : " + order.getId() + "</b></p>"
                                    + "<p>Order placed by : <b>" + order.getUser().getFirstName() + "</b></p>";

        for(OrderContent orderContent : orderContents) {
            if(orderContent.getProductType() == ProductType.DOWNLOADABLE) {
                Album album = albumRepository.findById(orderContent.getProductId()).orElse(null);

                if(album != null) {
                    orderEmailContent += //"<img src=\""+  G_DRIVE_THUMBNAIL + album.getCoverLink()  + ">" +
                            "<p>Album : <b>" + album.getName() + "</b><p>";

                    List<Song> songs = album.getSongs();
                    int index = 1;
                    for(Song song : songs) {
                        orderEmailContent += index + ": <a href=\"" +
                                                    G_DRIVE_DOWNLOAD_FRONT + song.getLink() +
                                                    G_DRIVE_DOWNLOAD_REAR +
                                                "\">"  +song.getName() +
                                            "</a>" ;
                    }
                }
            }
        }

      //  orderEmailContent += "</div>";

        emailService.sendMimeEmail(
                    order.getUser().getEmail(),
                    ALBUM_ORDER_MAIL_SUBJECT,
                orderEmailContent
               );

        //TODO: update the order status after sending the mail
        //orderRepository.
    }
}
