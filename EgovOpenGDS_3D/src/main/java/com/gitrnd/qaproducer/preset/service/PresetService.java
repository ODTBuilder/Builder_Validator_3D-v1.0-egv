package com.gitrnd.qaproducer.preset.service;

import java.util.ArrayList;
import java.util.List;

import com.gitrnd.qaproducer.preset.domain.Preset;

public interface PresetService {
	public Preset retrieveBasePreset(int cat);

	public Preset retrievePridByBasePreset(int cat);

	public Preset retrievePresetById(int pid);

	public Preset retrieveCatByPreset(int pid);

	public List<Preset> retrievePresetByUidx(int uidx);

	public List<Preset> retrievePresetNamesByUidx(int uidx);

	public void createPreset(Preset preset);

	public boolean updatePreset(Preset preset);

	public boolean deletePresets(ArrayList<Preset> prList);

	public Preset retrievePresetByIdAndUidx(Preset preset);

	public List<Preset> retrievePresetNameByNameAndUidx(Preset preset);

	public List<Preset> retrievePresetNameByNameAndUidxAndPid(Preset preset);
}
