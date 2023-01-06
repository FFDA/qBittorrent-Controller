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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentFile {
	// New format
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("size")
	@Expose
	private double size;
	@SerializedName("progress")
	@Expose
	private double progress;
	@SerializedName("priority")
	@Expose
	private int priority;
	@SerializedName("recyclerViewItemHeight")
	@Expose
	private int recyclerViewItemHeight;
	@SerializedName("isSeed")
	@Expose
	private boolean isSeed;
	@SerializedName("piece_range")
	@Expose
	private int[] piece_range;
	@SerializedName("douavailability")
	@Expose
	private double douavailability;

	public ContentFile(String name, double size, Double progress, int priority) {

		this.name = name;
		this.size = size;
		this.progress = progress;
		this.priority = priority;

	}

	public String getName() {
		return name;
	}

	public String getSize() {
		return Common.calculateSize(size);
	}

	public double getProgress() {
		return progress;
	}

	public int getPriority() {
		return priority;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public String getProgressAsString(){
		return String.format("%.2f", this.progress * 100) + "%";
	}

	public int getRecyclerViewItemHeight() {
		return recyclerViewItemHeight;
	}

	public void setRecyclerViewItemHeight(int recyclerViewItemHeight) {
		this.recyclerViewItemHeight = recyclerViewItemHeight;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public boolean isSeed() {
		return isSeed;
	}

	public void setSeed(boolean seed) {
		isSeed = seed;
	}

	public int[] getPiece_range() {
		return piece_range;
	}

	public void setPiece_range(int[] piece_range) {
		this.piece_range = piece_range;
	}

	public double getDouavailability() {
		return douavailability;
	}

	public void setDouavailability(double douavailability) {
		this.douavailability = douavailability;
	}
}
