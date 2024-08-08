import DashboardSidebar from '@components/dashboard/DashboardSidebar';
import { Outlet } from 'react-router-dom';
import S from './style';

export default function DashboardLayout() {
  return (
    <S.LayoutBg>
      <S.Layout>
        <DashboardSidebar />

        <S.MainContainer>
          <Outlet />
        </S.MainContainer>
      </S.Layout>
    </S.LayoutBg>
  );
}
