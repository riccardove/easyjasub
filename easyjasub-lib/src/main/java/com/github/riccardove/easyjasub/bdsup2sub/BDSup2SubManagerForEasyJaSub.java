package com.github.riccardove.easyjasub.bdsup2sub;

/*
 * #%L
 * easyjasub-lib
 * %%
 * Copyright (C) 2014 Riccardo Vestrini
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import bdsup2sub.BDSup2SubManager;

class BDSup2SubManagerForEasyJaSub implements BDSup2SubManager {

	@Override
	public boolean isCanceled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setProgress(long arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProgressMax(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean usesBT601() {
		// TODO Auto-generated method stub
		return false;
	}

}
