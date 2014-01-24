package ro.infoiasi.sedic.android.util;

import android.app.ProgressDialog;
import android.content.Context;

public final class DialogUtils {
	private static ProgressDialog progress;

	public static void showProgressDialog(Context ctx, String text) {
		if (progress != null) {
			progress.dismiss();
		}
		progress = ProgressDialog.show(ctx, null, text, true, false);
	}

	public static void hideProgressDialog(Context ctx) {
		if (progress != null) {
			progress.dismiss();
		}
	}

}
