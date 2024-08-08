import DashboardSidebar from '@components/dashboard/DashboardSidebar';
import { Outlet } from 'react-router-dom';
import S from './style';

export default function DashboardLayout() {
  const options = [{ text: '프론트엔드 7기 모집', isSelected: true, postId: 1 }];

  return (
    <S.LayoutBg>
      <S.Layout>
        <DashboardSidebar options={options} />

        <S.MainContainer>
          <Outlet />
        </S.MainContainer>
      </S.Layout>
    </S.LayoutBg>
  );
}
