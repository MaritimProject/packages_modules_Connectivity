/*
 * Copyright (C) 2019 The Android Open Source Project
 *
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
 */
package android.net;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

/**
 * This class is used to return the interface name and fd of the test interface
 *
 * @hide
 */
@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
public final class TestNetworkInterface implements Parcelable {
    @NonNull
    private final ParcelFileDescriptor mFileDescriptor;
    @NonNull
    private final String mInterfaceName;

    @Override
    public int describeContents() {
        return (mFileDescriptor != null) ? Parcelable.CONTENTS_FILE_DESCRIPTOR : 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeParcelable(mFileDescriptor, PARCELABLE_WRITE_RETURN_VALUE);
        out.writeString(mInterfaceName);
    }

    public TestNetworkInterface(@NonNull ParcelFileDescriptor pfd, @NonNull String intf) {
        mFileDescriptor = pfd;
        mInterfaceName = intf;
    }

    private TestNetworkInterface(@NonNull Parcel in) {
        mFileDescriptor = in.readParcelable(ParcelFileDescriptor.class.getClassLoader());
        mInterfaceName = in.readString();
    }

    @NonNull
    public ParcelFileDescriptor getFileDescriptor() {
        return mFileDescriptor;
    }

    @NonNull
    public String getInterfaceName() {
        return mInterfaceName;
    }

    @NonNull
    public static final Parcelable.Creator<TestNetworkInterface> CREATOR =
            new Parcelable.Creator<TestNetworkInterface>() {
                public TestNetworkInterface createFromParcel(Parcel in) {
                    return new TestNetworkInterface(in);
                }

                public TestNetworkInterface[] newArray(int size) {
                    return new TestNetworkInterface[size];
                }
            };
}
