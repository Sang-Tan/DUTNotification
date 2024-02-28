package com.htsml.dutnotif.crawl.notification;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class DUTNotificationCrawlerImpl implements DUTNotificationCrawler{
    private final DUTNotificationMapper notificationMapper;

    public DUTNotificationCrawlerImpl(DUTNotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    @Override
    public List<GroupNotificationDto> getGroupNotifications(int page) {
        if (page < 1) throw new IllegalArgumentException("Page must be greater than 0");

        String url = String.format("http://sv.dut.udn.vn/WebAjax/evLopHP_Load.aspx?E=CTRTBGV&PAGETB=%d&COL=TieuDe&NAME=&TAB=1", page);

        return crawlNotification(url)
                .stream()
                .map(notification -> {
                    GroupNotificationDto groupNotificationDto =
                            notificationMapper.toGroupNotificationDto(notification);
                    String title = notification.getTitle();
                    groupNotificationDto
                            .setGroup(title.substring(title.indexOf("[") + 1, title.indexOf("]")));

                    return groupNotificationDto;
                })
                .toList();
    }

    @Override
    public List<GeneralNotificationDto> getGeneralNotifications(int page) {
        if (page < 1) throw new IllegalArgumentException("Page must be greater than 0");

        String url = String.format("http://sv.dut.udn.vn/WebAjax/evLopHP_Load.aspx?E=CTRTBSV&PAGETB=%d&COL=TieuDe&NAME=&TAB=0", page);

        return crawlNotification(url)
                .stream()
                .map(notificationMapper::toGeneralNotificationDto)
                .toList();
    }

    private List<DUTNotificationDto> crawlNotification(String url){
        Document document = getDocument(url);
        var tbBoxes = document.getElementsByClass("tbBox");
        return tbBoxes.stream().map(tbBox -> {
            var tbBoxCaption = tbBox.child(0);
            var tbBoxContent = tbBox.child(1);
            var dateText = tbBoxCaption.child(0).child(0).text();
            var title = tbBoxCaption.child(1).text();

            DUTNotificationDto notificationDto = new DUTNotificationDto();
            notificationDto.setDate(getVietnameseDate(dateText.substring(0, dateText.length() - 1)));
            notificationDto.setTitle(title);
            notificationDto.setContent(tbBoxContent.text());

            return notificationDto;
        }).toList();
    }

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Date getVietnameseDate(String dateText) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(dateText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
