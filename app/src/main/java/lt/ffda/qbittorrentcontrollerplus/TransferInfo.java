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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferInfo {

    // New format
    @SerializedName("dl_info_speed")
    @Expose
    private long dl_info_speed;
    @SerializedName("dl_info_data")
    @Expose
    private long dl_info_data;
    @SerializedName("up_info_speed")
    @Expose
    private long up_info_speed;
    @SerializedName("up_info_data")
    @Expose
    private long up_info_data;
    @SerializedName("dl_rate_limit")
    @Expose
    private long dl_rate_limit;
    @SerializedName("up_rate_limit")
    @Expose
    private long up_rate_limit;
    @SerializedName("dht_nodes")
    @Expose
    private long dht_nodes;
    @SerializedName("connection_status")
    @Expose
    private String  connection_status;


    public long getDl_info_speed() {
        return dl_info_speed;
    }

    public void setDl_info_speed(long dl_info_speed) {
        this.dl_info_speed = dl_info_speed;
    }

    public long getDl_info_data() {
        return dl_info_data;
    }

    public void setDl_info_data(long dl_info_data) {
        this.dl_info_data = dl_info_data;
    }

    public long getUp_info_speed() {
        return up_info_speed;
    }

    public void setUp_info_speed(long up_info_speed) {
        this.up_info_speed = up_info_speed;
    }

    public long getUp_info_data() {
        return up_info_data;
    }

    public void setUp_info_data(long up_info_data) {
        this.up_info_data = up_info_data;
    }

    public long getDl_rate_limit() {
        return dl_rate_limit;
    }

    public void setDl_rate_limit(long dl_rate_limit) {
        this.dl_rate_limit = dl_rate_limit;
    }

    public long getUp_rate_limit() {
        return up_rate_limit;
    }

    public void setUp_rate_limit(long up_rate_limit) {
        this.up_rate_limit = up_rate_limit;
    }

    public long getDht_nodes() {
        return dht_nodes;
    }

    public void setDht_nodes(long dht_nodes) {
        this.dht_nodes = dht_nodes;
    }

    public String getConnection_status() {
        return connection_status;
    }

    public void setConnection_status(String connection_status) {
        this.connection_status = connection_status;
    }
}
