package be.fedict.perf.ocsp;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.bouncycastle.ocsp.CertificateID;
import org.bouncycastle.ocsp.OCSPException;
import org.bouncycastle.ocsp.OCSPReq;
import org.bouncycastle.ocsp.OCSPReqGenerator;

public class CertificateRepository {

	private final List<byte[]> ocspRequests;

	private Iterator<byte[]> ocspRequestIterator;

	private final byte[] ocspRequest;

	public CertificateRepository(boolean sameSerialNumber)
			throws CertificateException, OCSPException, IOException {
		this.ocspRequests = new LinkedList<byte[]>();

		System.out.println("Loading certificate repository...");

		InputStream certificatesConfigInputStream = CertificateRepository.class
				.getResourceAsStream("/be/fedict/perf/ocsp/certificates.config");
		if (null == certificatesConfigInputStream) {
			throw new RuntimeException("certificates.config not found");
		}

		Map<String, X509Certificate> caCertificates = new HashMap<String, X509Certificate>();
		CertificateFactory certificateFactory = CertificateFactory
				.getInstance("X.509");

		Scanner scanner = new Scanner(certificatesConfigInputStream);
		scanner.useDelimiter(",|\n");
		while (scanner.hasNextLine()) {
			String serialNumberStr = scanner.next();
			BigInteger certificateSerialNumber = new BigInteger(
					serialNumberStr, 16);
			String caAlias = scanner.next();
			X509Certificate caCertificate = caCertificates.get(caAlias);
			if (null == caCertificate) {
				InputStream caCertificateInputStream = CertificateRepository.class
						.getResourceAsStream("/be/fedict/perf/ocsp/" + caAlias
								+ ".crt");
				if (null == caCertificateInputStream) {
					throw new RuntimeException("missing CA certificate: "
							+ caAlias);
				}
				caCertificate = (X509Certificate) certificateFactory
						.generateCertificate(caCertificateInputStream);
				caCertificates.put(caAlias, caCertificate);
			}

			CertificateID certificateID = new CertificateID(
					CertificateID.HASH_SHA1, caCertificate,
					certificateSerialNumber);

			OCSPReqGenerator ocspReqGenerator = new OCSPReqGenerator();
			ocspReqGenerator.addRequest(certificateID);
			OCSPReq ocspReq = ocspReqGenerator.generate();
			byte[] ocspReqData = ocspReq.getEncoded();
			this.ocspRequests.add(ocspReqData);
		}

		if (this.ocspRequests.isEmpty()) {
			throw new RuntimeException("missing entries in certificates.config");
		}

		System.out.println("Shuffling repository...");
		Collections.shuffle(this.ocspRequests);

		this.ocspRequestIterator = this.ocspRequests.iterator();

		if (sameSerialNumber) {
			this.ocspRequest = this.ocspRequestIterator.next();
		} else {
			this.ocspRequest = null;
		}
	}

	public synchronized byte[] getOCSPRequest() {
		if (null != this.ocspRequest) {
			// always use the same in the case of sameSerialNumber
			return this.ocspRequest;
		}
		if (false == this.ocspRequestIterator.hasNext()) {
			this.ocspRequestIterator = this.ocspRequests.iterator();
		}
		return this.ocspRequestIterator.next();
	}

	public int getSize() {
		return this.ocspRequests.size();
	}
}
