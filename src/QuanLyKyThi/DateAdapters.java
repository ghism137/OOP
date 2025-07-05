package QuanLyKyThi;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAdapters {

    public static class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public LocalDate unmarshal(String v) throws Exception {
            return LocalDate.parse(v, FORMATTER);
        }

        @Override
        public String marshal(LocalDate v) throws Exception {
            return v.format(FORMATTER);
        }
    }

    public static class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public LocalDateTime unmarshal(String v) throws Exception {
            return LocalDateTime.parse(v, FORMATTER);
        }

        @Override
        public String marshal(LocalDateTime v) throws Exception {
            return v.format(FORMATTER);
        }
    }
}
