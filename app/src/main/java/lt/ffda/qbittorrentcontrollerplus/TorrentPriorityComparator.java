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

import java.util.Comparator;

public class TorrentPriorityComparator implements Comparator<Torrent> {

    boolean reversed = false;

    TorrentPriorityComparator(boolean reversed) {
        this.reversed = reversed;
    }

    @Override
    public int compare(Torrent t1, Torrent t2) {

//        String priority1 = t1.getPriority();
//        String priority2 = t2.getPriority();

        String priority1 = "" + t1.getPriority();
        String priority2 = "" + t2.getPriority();


        // If torrent has no priority, give it a really low priority for sorting purposes
        if (priority1 == null || priority1.equals("*")) {
            priority1 = "10000";
        }

        // If torrent has no priority, give it a really low priority for sorting purposes
        if (priority2 == null || priority2.equals("*")) {
            priority2 = "10000";
        }

        int p1 = Integer.parseInt(priority1);
        int p2 = Integer.parseInt(priority2);

        if (reversed) {
            return p2 - p1;
        } else {
            // Ascending order
            return p1 - p2;
        }
    }
}
