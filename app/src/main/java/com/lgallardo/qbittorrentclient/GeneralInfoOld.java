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
 * Created by lgallard on 6/11/18.
 */

public class GeneralInfoOld {
    private String path;
    private String creation_date;
    private String piece_size;
    private String comment;
    private String total_wasted;
    private String total_uploaded;
    private String total_downloaded;
    private String up_limit;
    private String dl_limit;
    private String nb_connections;
    private String share_ratio;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getPiece_size() {
        return piece_size;
    }

    public void setPiece_size(String piece_size) {
        this.piece_size = piece_size;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTotal_wasted() {
        return total_wasted;
    }

    public void setTotal_wasted(String total_wasted) {
        this.total_wasted = total_wasted;
    }

    public String getTotal_uploaded() {
        return total_uploaded;
    }

    public void setTotal_uploaded(String total_uploaded) {
        this.total_uploaded = total_uploaded;
    }

    public String getTotal_downloaded() {
        return total_downloaded;
    }

    public void setTotal_downloaded(String total_downloaded) {
        this.total_downloaded = total_downloaded;
    }

    public String getUp_limit() {
        return up_limit;
    }

    public void setUp_limit(String up_limit) {
        this.up_limit = up_limit;
    }

    public String getDl_limit() {
        return dl_limit;
    }

    public void setDl_limit(String dl_limit) {
        this.dl_limit = dl_limit;
    }

    public String getNb_connections() {
        return nb_connections;
    }

    public void setNb_connections(String nb_connections) {
        this.nb_connections = nb_connections;
    }

    public String getShare_ratio() {
        return share_ratio;
    }

    public void setShare_ratio(String share_ratio) {
        this.share_ratio = share_ratio;
    }
}
