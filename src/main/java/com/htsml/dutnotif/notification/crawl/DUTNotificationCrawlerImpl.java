package com.htsml.dutnotif.notification.crawl;

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
    @Override
    public List<GroupNotificationDto> getGroupNotifications(int page) {
        if (page < 1) throw new IllegalArgumentException("Page must be greater than 0");

        String url = String.format("http://sv.dut.udn.vn/WebAjax/evLopHP_Load.aspx?E=CTRTBGV&PAGETB=%d&COL=TieuDe&NAME=&TAB=1", page);
        Document document = getDocument(url);

        var tbBoxes = document.getElementsByClass("tbBox");
        return tbBoxes.stream().map(tbBox -> {
            var tbBoxCaption = tbBox.child(0);
            var tbBoxContent = tbBox.child(1);
            var dateText = tbBoxCaption.child(0).child(0).text();
            var title = tbBoxCaption.child(1).text();

            return GroupNotificationDto.builder()
                    .date(getVietnameseDate(dateText.substring(0, dateText.length() - 1)))
                    .title(title)
                    .content(tbBoxContent.text())
                    .group(title.substring(title.indexOf("[") + 1, title.indexOf("]")))
                    .build();
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
