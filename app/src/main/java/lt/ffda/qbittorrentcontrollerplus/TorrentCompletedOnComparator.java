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

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class TorrentCompletedOnComparator implements Comparator<Torrent> {

    boolean reversed = false;

    TorrentCompletedOnComparator(boolean reversed) {
        this.reversed = reversed;
    }

    @Override
    public int compare(Torrent t1, Torrent t2) {

        Date date1 = null;
        Date date2 = null;

//        String d1 = t1.getCompletionOn();
//        String d2 = t2.getCompletionOn();

        String d1 = "" + t1.getCompletion_on();
        String d2 = "" + t2.getCompletion_on();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        try {
            date1 = sdf.parse("1970-01-01T00:00:00");
            date1 = sdf.parse(d1);
        } catch (Exception e) {
        }

        try {
            date2 = sdf.parse("1970-01-01T00:00:00");
            date2 = sdf.parse(d2);
        } catch (Exception e) {
        }

        if (reversed) {
            // Ascending order
            return date1.compareTo(date2);
        } else {
            // Descending order
            return date2.compareTo(date1);

        }
    }
}

