package com.folyd.tuan.bean;

public class Goods {

	public String id;
	public String cid;
	public String title;
	public String partname;
	public String img;
	public String start_date;
	public String end_date;
	public String price;
	public String value;
	public String discount_amount;
	public String discount_percent;
	public String quantity_sold;
	public String keywords;
	public String remain_time;
	public String sitename;
	public String sitekey;
	public String goods_url;
	public String siteurl;
	public String oauth;
	public String detail_url;
	public String imgarray;
	public String comment_num;
	public String comment_url;
	public String special_tip;
	public String merchant;
	public String merchant_tel;
	public String merchant_addr;

	@Override
	public String toString() {
		return "Goods [id=" + id + ", cid=" + cid + ", title=" + title
				+ ", partname=" + partname + ", img=" + img + ", start_date="
				+ start_date + ", end_date=" + end_date + ", price=" + price
				+ ", value=" + value + ", discount_amount=" + discount_amount
				+ ", discount_percent=" + discount_percent + ", quantity_sold="
				+ quantity_sold + ", keywords=" + keywords + ", remain_time="
				+ remain_time + ", sitename=" + sitename + ", sitekey="
				+ sitekey + ", goods_url=" + goods_url + ", siteurl=" + siteurl
				+ ", oauth=" + oauth + ", detail_url=" + detail_url
				+ ", imgarray=" + imgarray + ", comment_num=" + comment_num
				+ ", comment_url=" + comment_url + ", special_tip="
				+ special_tip + ", merchant=" + merchant + ", merchant_tel="
				+ merchant_tel + ", merchant_addr=" + merchant_addr
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
