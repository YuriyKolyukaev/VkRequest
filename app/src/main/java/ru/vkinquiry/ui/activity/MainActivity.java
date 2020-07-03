package ru.vkinquiry.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;
import java.util.List;

import ru.vkinquiry.CurrentUser;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.R;
import ru.vkinquiry.model.Profile;
import ru.vkinquiry.mvp.presenter.MainPresenter;
import ru.vkinquiry.mvp.view.MainView;
import ru.vkinquiry.ui.fragment.BaseFragment;
import ru.vkinquiry.ui.fragment.NewsFeedFragment;

public class MainActivity extends BaseActivity implements MainView {

    /*Внедряем Presenter при помощи аннотации @InjectPresentor библиотеки Moxy, эта аннотация
    служит для управления жизненным циклом Presentor*/
    @InjectPresenter
    MainPresenter mainPresenter;

    private Drawer mDrawer;

    private AccountHeader mAccountHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getsApplicationComponent().inject(this);

        mainPresenter.checkAuth();
    }

    public void setUpDrawer() {
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.screen_name_news)
                .withIcon(GoogleMaterial.Icon.gmd_home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.screen_name_my_posts)
                .withIcon(GoogleMaterial.Icon.gmd_list);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.screen_name_settings)
                .withIcon(GoogleMaterial.Icon.gmd_settings);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.screen_name_members)
                .withIcon(GoogleMaterial.Icon.gmd_people);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.screen_name_topics)
                .withIcon(GoogleMaterial.Icon.gmd_record_voice_over);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6).withName(R.string.screen_name_info)
                .withIcon(GoogleMaterial.Icon.gmd_info);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(7).withName(R.string.screen_name_rules)
                .withIcon(GoogleMaterial.Icon.gmd_assignment);

        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(mAccountHeader)
                .addDrawerItems(item1, item2, item3,
                        new SectionDrawerItem().withName("Группа"),
                        item4, item5, item6, item7)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    mainPresenter.drawerItemClick((int) drawerItem.getIdentifier());
                    return false;
                })
                .build();
    }

    @Override
    protected int getMainContentLayout() {                                                          // возвращаем id layout в getMainContentLayout
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {                 // необходим для авторизации пользователя
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                mainPresenter.checkAuth();
            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void startSignIn() {
        VKSdk.login(this, VKScope.EMAIL, VKScope.AUDIO, VKScope.DIRECT, VKScope.VIDEO,
                VKScope.WALL, VKScope.PHOTOS, VKScope.GROUPS, VKScope.PAGES, VKScope.STATS, VKScope.DOCS);
    }

    @Override
    public void signedId() {
        Toast.makeText(this, "Current user id: " + CurrentUser.getId(), Toast.LENGTH_SHORT).show();
        setContent(new NewsFeedFragment());
        setUpDrawer();
    }

    @Override
    public void showCurrentUser(Profile profile) {
        List<IProfile> profileDrawerItems = new ArrayList<>();
        profileDrawerItems.add(new ProfileDrawerItem().withName(profile.getFullName())
                .withEmail(VKAccessToken.currentToken().email)
                .withIcon(profile.getDisplayProfilePhoto()));

        profileDrawerItems.add(new ProfileDrawerItem().withName("Logout")
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                            mAccountHeader.removeProfile(0);
                            mAccountHeader.removeProfile(0);
                            VKSdk.logout();
                            return false;
                        })
                );
        mAccountHeader.setProfiles(profileDrawerItems);
    }

    @Override
    public void showFragmentFromDrawer(BaseFragment baseFragment) {
        setContent(baseFragment);
    }
}
