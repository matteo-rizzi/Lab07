package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {

	private PowerOutageDAO podao;
	private List<PowerOutage> powerOutages = new ArrayList<>();
	private List<PowerOutage> solution = null;
	private int maxTotalAffected;
	private long totalHours;

	public Model() {
		podao = new PowerOutageDAO();
	}

	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> getPowerOutageListByNerc(Nerc nerc) {
		return podao.getPowerOutageListByNerc(nerc);
	}

	public List<PowerOutage> trovaWorstCase(Nerc nerc, int x, long y) {
		this.powerOutages = this.getPowerOutageListByNerc(nerc);
		solution = null;
		this.maxTotalAffected = 0;
		this.totalHours = 0;
		List<PowerOutage> parziale = new ArrayList<>();

		this.cerca(parziale, 0, x, y);
		return solution;

	}

	private void cerca(List<PowerOutage> parziale, int L, int x, long y) {

		// caso terminale
		if (this.solution == null || calcolaWorst(parziale) > calcolaWorst(solution)) {
			this.solution = new ArrayList<>(parziale);
			this.maxTotalAffected = calcolaWorst(solution);
			this.totalHours = getTotalHours(solution);
		}

		// caso intermedio
		for (PowerOutage po : powerOutages) {
			if ((!parziale.contains(po)) && aggiuntaValida(parziale, po, x, y)) {
				parziale.add(po);
				this.cerca(parziale, L + 1, x, y);
				parziale.remove(parziale.size() - 1);
			}
		}
	}

	private long getTotalHours(List<PowerOutage> solution) {
		long oreTotali = 0;
		for (PowerOutage po : solution) {
			Duration d = Duration.between(po.getDateEventBegan(), po.getDateEventFinished());
			long ore = d.getSeconds() / 3600;

			oreTotali += ore;
		}
		return oreTotali;
	}

	private int calcolaWorst(List<PowerOutage> parziale) {
		int totalAffected = 0;
		for (PowerOutage po : parziale) {
			totalAffected += po.getCustomersAffected();
		}
		return totalAffected;
	}

	private boolean aggiuntaValida(List<PowerOutage> parziale, PowerOutage po, int x, long y) {
		boolean valido = true;
		if (parziale.size() > 0) {
			LocalDate ultimo = parziale.get(0).getDateEventBegan().toLocalDate();
			LocalDate primo = po.getDateEventBegan().toLocalDate();
			int anni = Period.between(ultimo, primo).getYears();
			if (anni > x)
				valido = false;
		}

		long oreTotali = 0;
		for (PowerOutage prova : parziale) {
			Duration d = Duration.between(prova.getDateEventBegan(), prova.getDateEventFinished());
			long ore = d.getSeconds() / 3600;

			oreTotali += ore;
		}

		Duration d = Duration.between(po.getDateEventBegan(), po.getDateEventFinished());
		long oreDaAggiungere = d.getSeconds() / 3600;

		oreTotali += oreDaAggiungere;
		if (oreTotali >= y)
			valido = false;

		return valido;
	}

	public int getMaxTotalAffected() {
		return maxTotalAffected;
	}

	public long getTotalHours() {
		return totalHours;
	}

}
