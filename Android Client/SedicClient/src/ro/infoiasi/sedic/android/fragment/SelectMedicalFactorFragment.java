package ro.infoiasi.sedic.android.fragment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pl.polidea.treeview.InMemoryTreeStateManager;
import pl.polidea.treeview.TreeBuilder;
import pl.polidea.treeview.TreeStateManager;
import pl.polidea.treeview.TreeViewList;
import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.activity.MainActivity;
import ro.infoiasi.sedic.android.adapter.BeanTreeAdapter;
import ro.infoiasi.sedic.android.communication.event.GetMedicalConditionEvent;
import ro.infoiasi.sedic.android.model.Bean;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import ro.infoiasi.sedic.android.model.MedicalFactorBean;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import de.greenrobot.event.EventBus;

public class SelectMedicalFactorFragment extends Fragment {

	private final Set<Bean> selected = new HashSet<Bean>();

	@SuppressWarnings("unused")
	private static final String TAG = SelectMedicalFactorFragment.class.getSimpleName();
	private TreeViewList treeView;
	private ProgressBar progress;
	private View ageInputGroup;
	private EditText mAgeInput;
	private Handler handler = new Handler();

	private static final int LEVEL_NUMBER = 2;
	private TreeStateManager<Bean> manager = null;
	private BeanTreeAdapter<Bean> simpleAdapter;
	private boolean initialized = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = new InMemoryTreeStateManager<Bean>();
		initialized = initTreeManager();
		EventBus.getDefault().register(this, GetMedicalConditionEvent.class);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MainActivity) {
			((MainActivity) activity).registerMedicalFactorsFragment(this);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this, GetMedicalConditionEvent.class);
	}

	private boolean initTreeManager() {
		Map<Long, DiseaseBean> contraindicatedDiseases = SedicApplication.getInstance().getContraindicatedDiseases();
		Map<Long, MedicalFactorBean> medicalFactors = SedicApplication.getInstance().getMedicalFactors();
		if (contraindicatedDiseases != null || medicalFactors != null) {
			final TreeBuilder<Bean> treeBuilder = new TreeBuilder<Bean>(manager);
			if (contraindicatedDiseases != null) {
				Bean contraindicatedRoot = new Bean() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -6377853471229628464L;

					@Override
					public String getBeanName() {
						return "Contraindicated diseases";
					}

					@Override
					public long getBeanID() {
						return 1;
					}
				};
				treeBuilder.sequentiallyAddNextNode(contraindicatedRoot, 0);
				for (DiseaseBean bean : contraindicatedDiseases.values()) {
					treeBuilder.addRelation(contraindicatedRoot, bean);
				}
			}
			if (medicalFactors != null) {
				Bean medicalFactorsRoot = new Bean() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 6420779995732182167L;

					@Override
					public String getBeanName() {
						return "Medical Factors";
					}

					@Override
					public long getBeanID() {
						return 2;
					}
				};
				treeBuilder.sequentiallyAddNextNode(medicalFactorsRoot, 0);
				for (MedicalFactorBean bean : medicalFactors.values()) {
					treeBuilder.addRelation(medicalFactorsRoot, bean);
				}
			}

			return true;
		}
		return false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.select_medical_factors_layout, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		treeView = (TreeViewList) view.findViewById(R.id.tree_view);
		mAgeInput = (EditText) view.findViewById(R.id.smc_age_input);
		progress = (ProgressBar) view.findViewById(R.id.progress);
		ageInputGroup = view.findViewById(R.id.smc_upper);

		if (initialized) {
			ageInputGroup.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			simpleAdapter = new BeanTreeAdapter<Bean>(getActivity(), selected, manager, LEVEL_NUMBER, false);
			treeView.setAdapter(simpleAdapter);
			simpleAdapter.setCheckedChangedListener((MainActivity) getActivity());
			treeView.setCollapsible(true);
		} else {
			ageInputGroup.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
		}
	}

	public void onEventAsync(GetMedicalConditionEvent e) {
		if (!initialized) {
			handler.post(new Runnable() {

				@Override
				public void run() {
					progress.setVisibility(View.VISIBLE);
				}
			});
			initTreeManager();
			simpleAdapter = new BeanTreeAdapter<Bean>(getActivity(), selected, manager, LEVEL_NUMBER, false);
			handler.post(new Runnable() {

				@Override
				public void run() {
					treeView.setAdapter(simpleAdapter);
					simpleAdapter.setCheckedChangedListener((MainActivity) getActivity());
					treeView.setCollapsible(true);
					progress.setVisibility(View.GONE);
					ageInputGroup.setVisibility(View.VISIBLE);
				}
			});

		} else {
			handler.post(new Runnable() {

				@Override
				public void run() {
					progress.setVisibility(View.GONE);
				}
			});
		}
	}

	public Set<Bean> getSelection() {
		return selected;
	}

	public Integer getMinimumAge() {
		if (mAgeInput != null) {
			String strInput = mAgeInput.getText().toString();
			try {
				return Integer.valueOf(strInput);
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

}
