package com.explore.android.core.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebAddress {

	public static final String GOOD_IRI_CHAR = "a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";

	private String mScheme;
	private String mHost;
	private int mPort;
	private String mPath;
	private String mAuthInfo;

	static final int MATCH_GROUP_SCHEME = 1;
	static final int MATCH_GROUP_AUTHORITY = 2;
	static final int MATCH_GROUP_HOST = 3;
	static final int MATCH_GROUP_PORT = 4;
	static final int MATCH_GROUP_PATH = 5;
	
	static Pattern sAddressPattern = Pattern.compile(
			/* scheme    */ "(?:(http|https|file)\\:\\/\\/)?" +
			/* authority */ "(?:([-A-Za-z0-9$_.+!*'(),;?&=]+(?:\\:[-A-Za-z0-9$_.+!*'(),;?&=]+)?)@)?" +
			/* host      */ "([" + GOOD_IRI_CHAR + "%_-][" + GOOD_IRI_CHAR + "%_\\.-]*|\\[[0-9a-fA-F:\\.]+\\])?" +
			/* port      */ "(?:\\:([0-9]*))?" +
			/* path      */ "(\\/?[^#]*)?" +
			/* anchor    */ ".*", Pattern.CASE_INSENSITIVE);

	public WebAddress(String address) throws ExParseException {
		if (address == null) {
			throw new NullPointerException();
		}
		// android.util.Log.d(LOGTAG, "WebAddress: " + address);

		mScheme = "";
		mHost = "";
		mPort = -1;
		mPath = "/";
		mAuthInfo = "";

		Matcher m = sAddressPattern.matcher(address);
		String t;
		if (m.matches()) {
			t = m.group(MATCH_GROUP_SCHEME);
			if (t != null)
				mScheme = t.toLowerCase(Locale.ROOT);
			t = m.group(MATCH_GROUP_AUTHORITY);
			if (t != null)
				mAuthInfo = t;
			t = m.group(MATCH_GROUP_HOST);
			if (t != null)
				mHost = t;
			t = m.group(MATCH_GROUP_PORT);
			if (t != null && t.length() > 0) {
				// The ':' character is not returned by the regex.
				try {
					mPort = Integer.parseInt(t);
				} catch (NumberFormatException ex) {
					throw new ExParseException("Bad port");
				}
			}
			t = m.group(MATCH_GROUP_PATH);
			if (t != null && t.length() > 0) {
				/*
				 * handle busted myspace frontpage redirect with missing initial
				 * "/"
				 */
				if (t.charAt(0) == '/') {
					mPath = t;
				} else {
					mPath = "/" + t;
				}
			}

		} else {
			// nothing found... outa here
			throw new ExParseException("Bad address");
		}

		/*
		 * Get port from scheme or scheme from port, if necessary and possible
		 */
		if (mPort == 443 && mScheme.equals("")) {
			mScheme = "https";
		} else if (mPort == -1) {
			if (mScheme.equals("https"))
				mPort = 443;
			else
				mPort = 80; // default
		}
		if (mScheme.equals(""))
			mScheme = "http";
	}

	@Override
	public String toString() {
		String port = "";
		if ((mPort != 443 && mScheme.equals("https"))
				|| (mPort != 80 && mScheme.equals("http"))) {
			port = ":" + Integer.toString(mPort);
		}
		String authInfo = "";
		if (mAuthInfo.length() > 0) {
			authInfo = mAuthInfo + "@";
		}

		return mScheme + "://" + authInfo + mHost + port + mPath;
	}

	public String getmScheme() {
		return mScheme;
	}

	public void setmScheme(String mScheme) {
		this.mScheme = mScheme;
	}

	public String getmHost() {
		return mHost;
	}

	public void setmHost(String mHost) {
		this.mHost = mHost;
	}

	public int getmPort() {
		return mPort;
	}

	public void setmPort(int mPort) {
		this.mPort = mPort;
	}

	public String getmPath() {
		return mPath;
	}

	public void setmPath(String mPath) {
		this.mPath = mPath;
	}

	public String getmAuthInfo() {
		return mAuthInfo;
	}

	public void setmAuthInfo(String mAuthInfo) {
		this.mAuthInfo = mAuthInfo;
	}
	
}
