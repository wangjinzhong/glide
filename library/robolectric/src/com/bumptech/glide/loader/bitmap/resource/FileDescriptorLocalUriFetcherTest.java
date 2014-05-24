package com.bumptech.glide.loader.bitmap.resource;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.resize.Metadata;
import com.bumptech.glide.tests.ContentResolverShadow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = { ContentResolverShadow.class } )
public class FileDescriptorLocalUriFetcherTest {

    @Test
    public void testLoadsFileDescriptor() throws Exception {
        final Context context = Robolectric.application;
        final Uri uri = Uri.parse("file://nothing");

        ContentResolver contentResolver = context.getContentResolver();
        ContentResolverShadow shadow = Robolectric.shadowOf_(contentResolver);

        AssetFileDescriptor assetFileDescriptor = mock(AssetFileDescriptor.class);
        ParcelFileDescriptor parcelFileDescriptor = mock(ParcelFileDescriptor.class);
        when(assetFileDescriptor.getParcelFileDescriptor()).thenReturn(parcelFileDescriptor);
        shadow.registerFileDescriptor(uri, assetFileDescriptor);

        FileDescriptorLocalUriFetcher fetcher = new FileDescriptorLocalUriFetcher(context, uri);
        ParcelFileDescriptor descriptor = fetcher.loadResource(mock(Metadata.class));

        assertEquals(parcelFileDescriptor, descriptor);
    }

}
