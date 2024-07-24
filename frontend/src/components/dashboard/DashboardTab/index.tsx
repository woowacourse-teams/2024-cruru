import { NAV_BAR_MENU } from '@constants/constants';

import { Fragment } from 'react/jsx-runtime';
import S from './style';

export type DashboardMenus = 'applicant' | 'process';

interface NavBarProps {
  currentMenuKey: DashboardMenus;
  handleClickTabItem: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

export default function DashboardTab({ currentMenuKey, handleClickTabItem }: NavBarProps) {
  return (
    <S.Container>
      <S.Tabs>
        {Object.entries(NAV_BAR_MENU).map(([key, name]) => (
          <Fragment key={key}>
            <S.Tab>
              <S.TabButton
                name={key}
                type="button"
                onClick={handleClickTabItem}
                isCurrent={key === currentMenuKey}
              >
                {name}
              </S.TabButton>
            </S.Tab>
          </Fragment>
        ))}
      </S.Tabs>
    </S.Container>
  );
}
