package com.mohammadhajali.chat_F;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TapsAccessorAdapter extends FragmentPagerAdapter {
    public TapsAccessorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;

            case 1:
                ContactsFragment contacsFragment = new ContactsFragment();
                return contacsFragment;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Chats";

            case 1:
                return "Contacts";


            default:
                return null;
        }
    }
}
