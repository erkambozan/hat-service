package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class Datum {
	Addresses addresses;
	Checkout checkout;
	String code;
	Date created_at;
	String description;
	ExchangeRates exchange_rates;
	Date expires_at;
	boolean fees_settled;
	String hosted_url;
	String id;
	LocalExchangeRates local_exchange_rates;
	String logo_url;
	Metadata metadata;
	String name;
	String organization_name;
	PaymentThreshold payment_threshold;
	ArrayList<Payment> payments;
	Pricing pricing;
	String pricing_type;
	boolean pwcb_only;
	String resource;
	String support_email;
	ArrayList<Timeline> timeline;
	boolean utxo;
	Date confirmed_at;

	@JsonProperty("addresses")
	public Addresses getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Addresses addresses) {
		this.addresses = addresses;
	}

	@JsonProperty("checkout")
	public Checkout getCheckout() {
		return this.checkout;
	}

	public void setCheckout(Checkout checkout) {
		this.checkout = checkout;
	}

	@JsonProperty("code")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("created_at")
	public Date getCreated_at() {
		return this.created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	@JsonProperty("description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("exchange_rates")
	public ExchangeRates getExchange_rates() {
		return this.exchange_rates;
	}

	public void setExchange_rates(ExchangeRates exchange_rates) {
		this.exchange_rates = exchange_rates;
	}

	@JsonProperty("expires_at")
	public Date getExpires_at() {
		return this.expires_at;
	}

	public void setExpires_at(Date expires_at) {
		this.expires_at = expires_at;
	}

	@JsonProperty("fees_settled")
	public boolean getFees_settled() {
		return this.fees_settled;
	}

	public void setFees_settled(boolean fees_settled) {
		this.fees_settled = fees_settled;
	}

	@JsonProperty("hosted_url")
	public String getHosted_url() {
		return this.hosted_url;
	}

	public void setHosted_url(String hosted_url) {
		this.hosted_url = hosted_url;
	}

	@JsonProperty("id")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("local_exchange_rates")
	public LocalExchangeRates getLocal_exchange_rates() {
		return this.local_exchange_rates;
	}

	public void setLocal_exchange_rates(LocalExchangeRates local_exchange_rates) {
		this.local_exchange_rates = local_exchange_rates;
	}

	@JsonProperty("logo_url")
	public String getLogo_url() {
		return this.logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	@JsonProperty("metadata")
	public Metadata getMetadata() {
		return this.metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@JsonProperty("name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("organization_name")
	public String getOrganization_name() {
		return this.organization_name;
	}

	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}

	@JsonProperty("payment_threshold")
	public PaymentThreshold getPayment_threshold() {
		return this.payment_threshold;
	}

	public void setPayment_threshold(PaymentThreshold payment_threshold) {
		this.payment_threshold = payment_threshold;
	}

	@JsonProperty("payments")
	public ArrayList<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}

	@JsonProperty("pricing")
	public Pricing getPricing() {
		return this.pricing;
	}

	public void setPricing(Pricing pricing) {
		this.pricing = pricing;
	}

	@JsonProperty("pricing_type")
	public String getPricing_type() {
		return this.pricing_type;
	}

	public void setPricing_type(String pricing_type) {
		this.pricing_type = pricing_type;
	}

	@JsonProperty("pwcb_only")
	public boolean getPwcb_only() {
		return this.pwcb_only;
	}

	public void setPwcb_only(boolean pwcb_only) {
		this.pwcb_only = pwcb_only;
	}

	@JsonProperty("resource")
	public String getResource() {
		return this.resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@JsonProperty("support_email")
	public String getSupport_email() {
		return this.support_email;
	}

	public void setSupport_email(String support_email) {
		this.support_email = support_email;
	}

	@JsonProperty("timeline")
	public ArrayList<Timeline> getTimeline() {
		return this.timeline;
	}

	public void setTimeline(ArrayList<Timeline> timeline) {
		this.timeline = timeline;
	}

	@JsonProperty("utxo")
	public boolean getUtxo() {
		return this.utxo;
	}

	public void setUtxo(boolean utxo) {
		this.utxo = utxo;
	}

	@JsonProperty("confirmed_at")
	public Date getConfirmed_at() {
		return this.confirmed_at;
	}

	public void setConfirmed_at(Date confirmed_at) {
		this.confirmed_at = confirmed_at;
	}
}
