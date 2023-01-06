/*
 * Copyright (C) 2023 FFDA
 * This file is part of qBittorrent Controller+.
 *
 * qBittorrent Controller+ is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * qBittorrent Controller+ is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with qBittorrent Controller+. If not, see <https://www.gnu.org/licenses/>.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Original License
 * MIT License
 * Copyright (c) 2020 Luis M. Gallardo D.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lt.ffda.qbittorrentcontrollerplus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by lgallard on 1/30/15.
 */
public class Common {

    protected static String calculateSize(String value) {

        if(value == null || value.equals(""))
            return "0";

        long bytes = Long.parseLong(value);

        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp - 1) + "i";

        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre).replace(",", ".");

    }

    protected static String calculateSize(double value) {

        String result = "0 B";

        long bytes = (long) value;

        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp - 1) + "i";

        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre).replace(",", ".");

    }

    protected static double humanSizeToBytes(String value) {
        String scalar;
        int unit = 1024;
        int exp;
        char c;
        double returnValue = 0;


        String[] words = value.split("\\s+");

        if (words.length == 2) {

            try {
                scalar = words[0].replace(",", ".");

                exp = "BKMGTPE".indexOf((words[1]).toCharArray()[0]);

                returnValue = Double.parseDouble(scalar) * Math.pow(unit, exp);

            } catch (Exception e) {
                returnValue = 0;
            }
        }

        return returnValue;
    }

    protected static String unixTimestampToDate(String unixDate) {
        long dv = Long.valueOf(unixDate) * 1000;// its need to be in milliseconds
        Date df = new Date(dv);
        return new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(df);
    }

    protected static String unixTimestampToDate(long unixDate) {
//        long dv = Long.valueOf(unixDate) * 1000;// its need to be in milliseconds
        Date df = new Date(unixDate);
        return new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(df);
    }


    protected static String secondsToEta(long seconds) {

        String secs;
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);


        if (day >= 100) {
            secs = "∞";
        } else {

            if (day > 0) {
                secs = day + "d " + hours + "h";
            } else {

                if (hours > 0) {
                    secs = hours + "h " + minute + "m";
                } else {

                    if (minute > 0) {
                        secs = minute + "m";
                    } else {
                        secs = second + "s";
                    }
                }
            }
        }

        return secs;
    }

    // This method converts unix timestamp to date representation
    protected static String timestampToDate(String timestamp){

        long unixtimestamp = new Long(timestamp);

        if(unixtimestamp == Long.parseLong("4294967295")){
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        Date date = new Date(unixtimestamp*1000);
        return new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(date);
    }

    protected static String timestampToDate(long unixtimestamp){


        if(unixtimestamp == Long.parseLong("4294967295")){
            return "";
        }

        Date date = new Date(unixtimestamp*1000);
        return new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(date);
    }

    // Taken from https://stackoverflow.com/questions/10039672/android-how-to-read-file-in-bytes

    protected static byte[] fullyReadFileToBytes(File f) throws IOException {
        int size = (int) f.length();
        byte bytes[] = new byte[size];
        byte tmpBuff[] = new byte[size];
        FileInputStream fis = new FileInputStream(f);

        try {

            int read = fis.read(bytes, 0, size);
            if (read < size) {
                int remain = size - read;
                while (remain > 0) {
                    read = fis.read(tmpBuff, 0, remain);
                    System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                    remain -= read;
                }
            }
        } finally {
            fis.close();
        }

        return bytes;
    }

    protected static String ProgressForUi(double progress){
        // Format progress for UI
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(progress * 100);
    }

    protected static String ProgressForUiTruncated(double progress){
        // Format progress for UI
        DecimalFormat df = new DecimalFormat("0");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(progress * 100);
    }

    protected static String RatioForUi(double ratio){
        // Format progress for UI
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(ratio);
    }

    protected static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
