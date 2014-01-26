package ro.infoiasi.sedic.android.activity;

import java.util.List;
import java.util.Map;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import ro.infoiasi.sedic.android.model.DrugBean;
import ro.infoiasi.sedic.android.model.MedicalConditionBean;
import ro.infoiasi.sedic.android.model.MedicalFactorBean;
import ro.infoiasi.sedic.android.model.PlantBean;
import ro.infoiasi.sedic.android.model.RemedyBean;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class RemedyDetailActivity extends ActionBarActivity {

	public static final String INTENT_EXTRA_REMEDY_ID = "intent_extra_remedy_id";

	@SuppressWarnings("unused")
	private static final String tag = RemedyDetailActivity.class.getSimpleName();
	private RemedyBean mRemedyBean;
	private PlantBean mRemedyPlant;
	private long mRemedyId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.remedy_detail_layout);
		if (getIntent().hasExtra(INTENT_EXTRA_REMEDY_ID)) {
			mRemedyId = getIntent().getLongExtra(INTENT_EXTRA_REMEDY_ID, -1);
		} else {
			mRemedyId = -1;
		}
		setupData();
		if (mRemedyBean != null) {
			getSupportActionBar().setTitle(mRemedyBean.getRemedyName());
		}
		setupUI();
	}

	private void setupData() {
		if (mRemedyId != -1) {
			Map<Long, RemedyBean> remedies = SedicApplication.getInstance().getRemedies();
			if (remedies != null) {
				mRemedyBean = remedies.get(Long.valueOf(mRemedyId));

				long plantId = mRemedyBean.getRemedyPlantId();
				mRemedyPlant = SedicApplication.getInstance().getPlantList().get(plantId);
			}
		} else {
			Toast.makeText(this, "remedy could not be found", Toast.LENGTH_LONG).show();
			finish();
		}

	}

	private void setupUI() {
		TextView aboutLabel = (TextView) findViewById(R.id.rd_about_label);
		TextView remedyDescription = (TextView) findViewById(R.id.rd_description);
		TextView partPlant = (TextView) findViewById(R.id.rd_plant_part);
		TextView medicalConditionLabel = (TextView) findViewById(R.id.rd_mc_label);
		TextView medicalConditionView = (TextView) findViewById(R.id.rd_medical_condition);
		TextView therapeuticalUsageView = (TextView) findViewById(R.id.rd_therapeutical);
		TextView adjuvantUsageView = (TextView) findViewById(R.id.rd_adjuvant);
		if (mRemedyPlant != null) {
			aboutLabel.setText("About " + mRemedyPlant.getPlantName());
			remedyDescription.setText(Html.fromHtml(mRemedyPlant.getPlantDescription()));
			medicalConditionLabel.setText("Medical Conditions and Interactions for " + mRemedyPlant.getPlantName());
		}

		if (mRemedyBean != null) {
			List<String> plantParts = mRemedyBean.getPartPlantUsages();
			if (plantParts.size() > 0) {
				StringBuffer plantPartBuffer = new StringBuffer();
				for (int i = 0; i < plantParts.size() - 1; i++) {
					plantPartBuffer.append(plantParts.get(i) + ", ");
				}
				plantPartBuffer.append(plantParts.get(plantParts.size() - 1));
				partPlant.setText(plantPartBuffer.toString());
			}
			MedicalConditionBean medicalCondition = mRemedyBean.getMedicalCondition();
			if (medicalCondition != null) {
				StringBuffer buf = new StringBuffer();
				buf.append("Do not use for individuals less than " + medicalCondition.getMedicalConditionMinAge()
						+ " years old unless recommended by a physician.\n");
				if (medicalCondition.getMedicalFactors() != null && !medicalCondition.getMedicalFactors().isEmpty()) {
					buf.append("\nMedical factors to be avoided: \n");
					for (MedicalFactorBean mf : medicalCondition.getMedicalFactors()) {
						buf.append(mf.getMedicalFactorName() + "\n");
					}
					buf.append("\n");
				}

				if (medicalCondition.getContraindicatedDiseases() != null
						&& !medicalCondition.getContraindicatedDiseases().isEmpty()) {
					buf.append("Contraindicated diseases: \n");
					for (DiseaseBean disease : medicalCondition.getContraindicatedDiseases()) {
						buf.append(disease.getDiseaseName() + "\n");
					}
				}
				medicalConditionView.setText(buf.toString());
			}

			if (mRemedyBean.getAdjuvantUsages() != null && !mRemedyBean.getAdjuvantUsages().isEmpty()) {
				StringBuffer buf = new StringBuffer();
				for (DrugBean bean : mRemedyBean.getAdjuvantUsages()) {
					buf.append(bean.getDrugName() + "\n");
				}
				adjuvantUsageView.setText(buf.toString());
			} else {
				adjuvantUsageView.setText("No adjuvant usages for this remedy.");

			}
			if (mRemedyBean.getTherapeuticalUsages() != null && !mRemedyBean.getTherapeuticalUsages().isEmpty()) {
				StringBuffer buf = new StringBuffer();
				for (DiseaseBean bean : mRemedyBean.getTherapeuticalUsages()) {
					buf.append(bean.getDiseaseName() + "\n");
				}
				therapeuticalUsageView.setText(buf.toString());
			} else {
				therapeuticalUsageView.setText("No therapeutical usages for this remedy.");

			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
