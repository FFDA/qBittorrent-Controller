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
package com.lgallardo.qbittorrentclient;
/**
 * Created by lgallard on 17/11/15.
 */
public class CustomLogger {


    public static String reportMainLog = "";
    public static String reportNotifierLog = "";
    public static String reportDescription = "";

    public static boolean mainActivityReporting = false;


    public static void saveReportMessage(String tag, String message) {

        if(tag.equals("Main")){

            if (CustomLogger.reportMainLog.equals("")){

                CustomLogger.reportMainLog = "\n[" + tag + "]";
            }
            CustomLogger.reportMainLog = CustomLogger.reportMainLog + "\n " + message;
        }

        if(tag.equals("Notifier")){
            if (CustomLogger.reportNotifierLog.equals("")){

                CustomLogger.reportNotifierLog = "\n[" + tag + "]";
            }
            CustomLogger.reportNotifierLog = CustomLogger.reportNotifierLog + "\n " + message;

        }

    }

    public static String getReport() {

        String report = getReportDescription() + "\n\n" + reportMainLog + "\n" + reportNotifierLog;

        return report;
    }

    public static void deleteMainReport() {
        reportMainLog = "";
    }

    public static void deleteNotifierReport() {
        reportNotifierLog = "";
    }


    public static boolean isMainActivityReporting() {
        return mainActivityReporting;
    }

    public static void setMainActivityReporting(boolean mainActivityReporting) {
        CustomLogger.mainActivityReporting = mainActivityReporting;
    }


    public static String getReportDescription() {
        return reportDescription;
    }

    public static void setReportDescription(String reportDescription) {
        CustomLogger.reportDescription = reportDescription;
    }
}